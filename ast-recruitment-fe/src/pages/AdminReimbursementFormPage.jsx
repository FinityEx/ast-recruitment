import { useEffect, useId, useState } from 'react';
import axios from 'axios';
import API_PATHS from '../apiPaths';
import FooterPortal from '../components/FooterPortal';
import { transformTextToCapitalize } from '../helpers';
import { uniqueId } from 'lodash';
import classNames from 'classnames';
import { useNavigate } from 'react-router-dom';

export default function AdminReimbursementFormPage() {
  const navigate = useNavigate();
  const formId = useId();
  const [adminRates, setAdminRates] = useState([]);
  const [reimbursementOptions, setReimbursementOptions] = useState([]);

  const onSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      carUsageRate: adminRates.find(({ type }) => type === 'CAR_USAGE_RATE')?.value,
      dailyAllowanceRate: adminRates.find(({ type }) => type === 'DAILY_ALLOWANCE_RATE')?.value,
      compensationLimit: adminRates.find(({ type }) => type === 'COMPENSATION_LIMIT')?.value || 0,
      reimbursementList: [
        ...reimbursementOptions.map(({ type, available }) => ({
          type,
          enabled: available
        }))
      ]
    };

    await axios
      .post(process.env.REACT_APP_API_URL + API_PATHS.RATES, JSON.stringify(payload))
      .then(() => alert('Changes have been saved!'))
      .catch((err) => console.error(err));
  };

  const adminAuthentication = () => {
    if (process.env.REACT_APP_MODE !== 'dev') {
      if (localStorage.getItem('token') === null) {
        let person = prompt('Admin password: ');

        if (process.env.REACT_APP_ADMIN_PASSWORD === person) {
          localStorage.setItem('token', '');
        } else {
          navigate(-1);
        }
      }
    }
  };

  useEffect(() => {
    adminAuthentication();

    Promise.all([
      axios.get(process.env.REACT_APP_API_URL + API_PATHS.REIMBURSEMENT_OPTIONS),
      axios.get(process.env.REACT_APP_API_URL + API_PATHS.RATES)
    ])
      .then((res) => {
        const [reimbursements, rates] = res;

        setAdminRates(
          rates.data.map(([type, value, description]) => ({
            type,
            value: Number(value) || null,
            description
          }))
        );

        setReimbursementOptions(
          Object.entries(reimbursements.data)
            .filter(([, value]) => value === 'RECEIPT')
            .map(([key, value]) => ({ type: key, available: true, value }))
        );
      })
      .catch((err) => console.error(err));

    return () => localStorage.removeItem('token');
  }, []);

  if (!adminRates.length && !reimbursementOptions.length) return <></>;

  const handleOnChange = (e, i) => {
    const { value } = e.target;

    setAdminRates((prevState) => {
      prevState[i].value = Number(value);

      return [...prevState];
    });
  };

  function handleRatesCheckboxesChange(e, i) {
    const { checked } = e.target;

    setAdminRates((prevState) => {
      prevState[i].value = checked ? 0 : null;

      return [...prevState];
    });
  }

  function handleReimbursementsCheckboxesChange(e, i) {
    const { checked } = e.target;

    setReimbursementOptions((prevState) => {
      prevState[i].available = checked;

      return [...prevState];
    });
  }

  return (
    <div className="d-flex justify-content-center align-items-center flex-column ">
      <h1 className="display-3 w-100 text-center mb-4">Admin Dashboard</h1>
      <form className="container row gap-4" onSubmit={onSubmit} id={formId}>
        <div className="col row gap-4">
          <div className="col-12">
            <h2 className="h4">Available rates</h2>
            {adminRates.map((a, i) => {
              const id = uniqueId(a.type);
              return (
                <div key={a.type} className="form-check">
                  <input
                    className="form-check-input"
                    type="checkbox"
                    checked={a.value !== null}
                    id={id}
                    onChange={(e) => handleRatesCheckboxesChange(e, i)}
                  />
                  <label className="form-check-label" htmlFor={id}>
                    {transformTextToCapitalize(a.type)}
                  </label>
                </div>
              );
            })}
          </div>
          <div className="col-12">
            <h2 className="h4">Available reimbursements</h2>
            {reimbursementOptions.map((r, i) => {
              const id = uniqueId(r.type);
              return (
                <div key={r.type} className="form-check">
                  <input
                    className="form-check-input"
                    type="checkbox"
                    checked={r.available}
                    id={id}
                    onChange={(e) => handleReimbursementsCheckboxesChange(e, i)}
                  />
                  <label className="form-check-label" htmlFor={id}>
                    {transformTextToCapitalize(r.type)}
                  </label>
                </div>
              );
            })}
          </div>
        </div>

        <div className="col">
          <h2 className="h4">Rates</h2>
          {adminRates.map((a, i) => (
            <div key={a.type} className="mb-3">
              <label
                htmlFor={a.type}
                className={classNames('form-label', {
                  'text-decoration-line-through': a.value === null
                })}>
                {transformTextToCapitalize(a.type)}({a.description})
              </label>
              <input
                type="number"
                className="form-control"
                id={a.type}
                value={a.value || 0}
                disabled={a.value === null}
                onChange={(e) => handleOnChange(e, i)}
              />
            </div>
          ))}
        </div>

        <FooterPortal>
          <div className="d-grid gap-2 w-100">
            <button type="submit" className="btn btn-success btn-lg rounded-0" form={formId}>
              Click to submit
            </button>
          </div>
        </FooterPortal>
      </form>
    </div>
  );
}
