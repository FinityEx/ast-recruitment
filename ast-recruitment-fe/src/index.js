import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import ErrorPage from './pages/ErrorPage';
import ROUTES from './routes';
import UserReimbursementFormPage from './pages/UserReimbursementFormPage';
import AdminReimbursementFormPage from './pages/AdminReimbursementFormPage';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle';
import HomePage from './pages/HomePage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: ROUTES.HOME_PAGE,
        element: <HomePage />
      },
      {
        path: ROUTES.USER_REIMBURSEMENT_FORM_PAGE,
        element: <UserReimbursementFormPage />
      },
      {
        path: ROUTES.ADMIN_REIMBURSEMENT_FORM_PAGE,
        element: <AdminReimbursementFormPage />
      }
    ]
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
