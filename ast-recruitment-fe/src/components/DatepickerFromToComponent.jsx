import PropTypes from 'prop-types';
import { useForm } from 'react-hook-form';
import { useEffect, useState } from 'react';
import moment from 'moment';

export default function DatepickerFromToComponent({ title, setValue }) {
  const [selectedDays, setSeletedDays] = useState([]);
  const { register, watch } = useForm();

  useEffect(() => {
    const subscription = watch(({ fromDate, toDate }) => {
      if (fromDate && toDate) {
        const days = [];
        const dateFrom = new moment(fromDate);
        const dateTo = new moment(toDate);
        const diff = dateTo.diff(dateFrom, 'days') + 1;

        for (let i = 0; i < diff; i++) {
          days.push({
            date: new moment(dateFrom).add(i, 'days').format('YYYY-MM-DD'),
            selected: true
          });
        }

        setValue(days.map((d) => d.date));
        setSeletedDays([...days]);
      }
    });

    return () => subscription.unsubscribe();
  }, [watch]);

  const onUnselectDay = (i) => {
    const result = [...selectedDays];
    result[i] = { ...result[i], selected: !result[i].selected };

    setValue(result.filter((d) => d.selected).map((d) => d.date));
    setSeletedDays([...result]);
  };

  return (
    <div className="d-flex justify-content-center align-items-center gap-2 flex-column w-100 mb-4">
      <h5 className="h4">{title}</h5>
      <div className="d-flex justify-content-center align-items-center gap-4">
        <div className="mb-3">
          <label htmlFor="dateFrom" className="form-label">
            From
          </label>
          <input type="date" className="form-control" id="dateFrom" {...register('fromDate')} />
        </div>

        <div className="mb-3">
          <label htmlFor="dateTo" className="form-label">
            To
          </label>
          <input type="date" className="form-control" id="dateTo" {...register('toDate')} />
        </div>
      </div>
      <ul className="list-group w-100 overflow-y-auto card" style={{ height: 355 }}>
        {selectedDays.map((d, i) => (
          <li className="list-group-item" key={i}>
            <input
              className="form-check-input me-1"
              type="checkbox"
              checked={d.selected}
              onChange={() => onUnselectDay(i)}
              id={'day' + i}
            />
            <label className="form-check-label" htmlFor={'day' + i}>
              {d.date}
            </label>
          </li>
        ))}
      </ul>
    </div>
  );
}

DatepickerFromToComponent.propTypes = {
  title: PropTypes.string,
  setValue: PropTypes.func.isRequired
};
