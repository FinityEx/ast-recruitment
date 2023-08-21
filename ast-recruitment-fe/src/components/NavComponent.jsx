import ROUTES from '../routes';
import { useLocation } from 'react-router-dom';
import classNames from 'classnames';

export default function NavComponent() {
  const location = useLocation();
  const isHomePage = () => location.pathname.includes(ROUTES.HOME_PAGE);
  const isAdminReimbursement = () =>
    location.pathname.includes(ROUTES.ADMIN_REIMBURSEMENT_FORM_PAGE);

  return (
    <nav className="navbar navbar-expand-lg bg-dark fs-5 flex-shrink-0" data-bs-theme="dark">
      <div className="container-fluid flex justify-content-center align-items-center">
        <ul className="navbar-nav mb-lg-0 w-100 text-center flex-row justify-content-center gap-4">
          <li className="nav-item">
            <a
              className={classNames('nav-link', { active: isHomePage() })}
              aria-current="page"
              href={ROUTES.HOME_PAGE}>
              Home
            </a>
          </li>
          <li className="nav-item">
            <a
              className={classNames('nav-link', { active: isAdminReimbursement() })}
              href={ROUTES.ADMIN_REIMBURSEMENT_FORM_PAGE}>
              Admin
            </a>
          </li>
        </ul>
      </div>
    </nav>
  );
}
