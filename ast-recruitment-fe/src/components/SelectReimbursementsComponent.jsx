import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import PropTypes from 'prop-types';
import { transformTextToCapitalize } from '../helpers';

export default function SelectReimbursementsComponent({ title, setValue, reimbursementOptions }) {
  const { register, watch } = useForm();

  const [selectedReimbursementOptions, setSelectedReimbursementOptions] = useState([]);

  useEffect(() => {
    const subscription = watch(({ selectedReimbursements }) => {
      const result = [];

      for (let i = 0; i < selectedReimbursements.length; i++) {
        const element = reimbursementOptions.find((r) => r.type === selectedReimbursements[i]);

        if (element) {
          result.push(element);
        }
      }

      setValue(result.map((s) => ({ type: s.type, inputNumber: s.inputNumber })));
      setSelectedReimbursementOptions([...result]);
    });

    return () => subscription.unsubscribe();
  }, [watch]);

  const handleOnChange = (s, e) => {
    const result = [...selectedReimbursementOptions];
    const selectedItem = result.find((r) => r.type === s.type);

    if (selectedItem) {
      const index = result.map((r) => r.type).indexOf(selectedItem.type);

      result[index] = {
        type: s.type,
        inputNumber: Number(e.target.value)
      };

      setValue(result);
      setSelectedReimbursementOptions(result);
    }
  };

  return (
    <div className="text-center">
      <h5 className="h4">{title}</h5>
      <select
        className="form-select"
        size={reimbursementOptions.length}
        multiple
        {...register('selectedReimbursements')}>
        {reimbursementOptions.map(
          (r, i) =>
            r.type !== 'DAILY_ALLOWANCE' && (
              <option key={i} value={r.type}>
                {transformTextToCapitalize(r.type)}
              </option>
            )
        )}
      </select>
      <ul className="list-group mt-4">
        {selectedReimbursementOptions.map((s, i) => (
          <li key={i} className="list-group-item ">
            <div className="d-flex justify-content-center align-items-center ">
              <label htmlFor={'reimbursement' + i} className="form-label w-100 align-middle mt-2">
                {transformTextToCapitalize(s.type)}($)
              </label>
              <input
                type="number"
                className="form-control"
                id={'reimbursement' + i}
                value={s.inputNumber || 0}
                onChange={(e) => handleOnChange(s, e)}
              />
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

SelectReimbursementsComponent.propTypes = {
  title: PropTypes.string,
  setValue: PropTypes.func.isRequired,
  reimbursementOptions: PropTypes.array.isRequired
};
