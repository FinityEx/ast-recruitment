import { useForm } from 'react-hook-form';
import { useEffect, useId, useState } from 'react';
import axios from 'axios';
import API_PATHS from '../apiPaths';
import DatepickerFromToComponent from '../components/DatepickerFromToComponent';
import SelectReimbursementsComponent from '../components/SelectReimbursementsComponent';
import FooterPortal from '../components/FooterPortal';

export default function UserReimbursementFormPage() {
  const formId = useId();
  const { handleSubmit, setValue } = useForm({
    defaultValues: {
      dayDateList: [],
      reimbursementList: [],
      otherReimbursementOptions: []
    }
  });
  const [reimbursementOptions, setReimbursementOptions] = useState([]);
  const [dailyAllowance, setDailyAllowance] = useState(false);
  const [carUsage, setCarUsage] = useState({
    checked: false,
    value: 0
  });
  const [descriptions, setDescriptions] = useState([]);
  const onSubmit = async ({ dayDateList, reimbursementList, otherReimbursementOptions }) => {
    const preparedOtherReimbursementOptions = Object.entries(otherReimbursementOptions)
      .filter(([, value]) => value)
      .map(([key]) => ({ type: key }));

    const reimbursementListPayload = [...reimbursementList, ...preparedOtherReimbursementOptions];

    if (dailyAllowance) {
      reimbursementListPayload.push({
        type: 'DAILY_ALLOWANCE'
      });
    }

    if (carUsage.checked) {
      reimbursementListPayload.push({
        type: 'CAR_USAGE',
        inputNumber: carUsage.value
      });
    }

    const payload = {
      dayDateList: dayDateList,
      reimbursementList: reimbursementListPayload
    };

    axios
      .post(process.env.REACT_APP_API_URL + API_PATHS.MAKE_CLAIM, JSON.stringify(payload))
      .then((res) => alert(`Your reimbursement: ${res.data}$`))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    Promise.all([
      axios.get(process.env.REACT_APP_API_URL + API_PATHS.REIMBURSEMENT_OPTIONS_ENABLED),
      axios.get(process.env.REACT_APP_API_URL + API_PATHS.RATES)
    ])
      .then((res) => {
        const [reimbursements, rates] = res;

        const result = Object.entries(reimbursements.data)
          .filter(([, value]) => value !== 'OTHER')
          .map((el) => {
            const [key] = el;
            return { type: key, inputNumber: 0 };
          });
        setReimbursementOptions(result);

        setDescriptions(
          rates.data.map(([type, value, description]) => ({
            type,
            value: Number(value) || null,
            description
          }))
        );
      })
      .catch((err) => console.error(err));
  }, []);

  function handleDailyAllowance(e) {
    const { checked } = e.target;

    setDailyAllowance(checked);
  }

  const getDescription = (type) => descriptions.find((desc) => desc.type === type);

  const handleCarUsageCheckbox = (e) => {
    const { checked } = e.target;

    setCarUsage({ ...carUsage, checked });
  };
  const handleCarUsageInput = (e) => {
    const { value } = e.target;

    setCarUsage({ ...carUsage, value: Number(value) });
  };
  return (
    <form className="container w-100 h-100" onSubmit={handleSubmit(onSubmit)} id={formId}>
      <h1 className="display-3 w-100 text-center mb-4">Reimbursement Form</h1>
      <div className="row">
        <div className="col">
          <DatepickerFromToComponent
            title="Trip date"
            setValue={(value) => setValue('dayDateList', value)}
          />
        </div>
        {reimbursementOptions.length && (
          <div className="col">
            <div className="row">
              <h5 className="h4 text-center">Reimbursements</h5>
              <div className="col-12">
                <div className="form-check">
                  {getDescription('DAILY_ALLOWANCE_RATE').value && (
                    <>
                      <input
                        className="form-check-input"
                        type="checkbox"
                        checked={dailyAllowance}
                        id="dailyAllowanceCheckbox"
                        onChange={handleDailyAllowance}
                      />
                      <label className="form-check-label" htmlFor="dailyAllowanceCheckbox">
                        Daily Allowance  (
                        {getDescription('DAILY_ALLOWANCE_RATE').description}:
                        {Number(getDescription('DAILY_ALLOWANCE_RATE').value).toFixed(2)})
                      </label>
                    </>
                  )}

                  {getDescription('CAR_USAGE_RATE').value && (
                    <div className="form-input">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        checked={carUsage.checked}
                        id="CarUsageCheckBox"
                        onChange={(e) => handleCarUsageCheckbox(e)}
                      />
                      <label className="form-label" htmlFor="CarUsageCheckBox">
                        Car Usage (
                        {getDescription('CAR_USAGE_RATE').description}:
                        {Number(getDescription('CAR_USAGE_RATE').value).toFixed(2)})
                      </label>
                      <input
                        type="number"
                        className="form-control"
                        id="CarUsageCheckInput"
                        value={carUsage.value}
                        disabled={!carUsage.checked}
                        onChange={(e) => handleCarUsageInput(e)}
                      />
                    </div>
                  )}
                </div>
              </div>
              <div className="col-12">
                <SelectReimbursementsComponent
                  setValue={(value) => setValue('reimbursementList', value)}
                  reimbursementOptions={reimbursementOptions}
                />
              </div>
            </div>
          </div>
        )}
      </div>

      <FooterPortal>
        <div className="d-grid gap-2 w-100">
          <button type="submit" className="btn btn-success btn-lg rounded-0" form={formId}>
            Click to submit
          </button>
        </div>
      </FooterPortal>
    </form>
  );
}
