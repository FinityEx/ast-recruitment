import { useNavigate } from 'react-router-dom';
import ROUTES from '../routes';

export default function HomePage() {
  const navigate = useNavigate();
  const onClick = () => navigate('/' + ROUTES.USER_REIMBURSEMENT_FORM_PAGE);

  return (
    <div className="text-center">
      <h1 className="display-1">Business expenses refund</h1>
      <p className="display-5">
        To create a request for reimbursement, click the button below
      </p>
      <div className="d-grid gap-2">
        <button type="button" className="btn btn-success btn-lg rounded-0" onClick={onClick}>
          Make a claim
        </button>
      </div>
    </div>
  );
}
