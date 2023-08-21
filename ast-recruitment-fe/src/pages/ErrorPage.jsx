import { useNavigate, useRouteError } from 'react-router-dom';

export default function ErrorPage() {
  const error = useRouteError();
  const navigate = useNavigate();
  const onClick = () => navigate(-1);
  return (
    <div className="vw-100 vh-100 d-flex flex-column justify-content-center align-items-center text-center">
      <h1 className="display-1">Oops!</h1>
      <p className="display-3">Sorry, an unexpected error has occurred.</p>
      <p className="display-6">
        <i>{error.statusText || error.message}</i>
      </p>
      <button
        type="button"
        className="btn btn-link fs-3 text-decoration-none pt-0"
        onClick={onClick}>
        Go Back
      </button>
    </div>
  );
}
