import NavComponent from './components/NavComponent';
import { Outlet } from 'react-router-dom';

function App() {
  return (
    <div className="vw-100 vh-100 d-flex flex-column">
      <NavComponent />
      <div className="overflow-y-scroll flex-grow-1">
        <Outlet />
      </div>
      <div
        id="portal-footer"
        className="flex-shrink-0 bg-dark fs-5 d-flex justify-content-center align-items-center mt-4"></div>
    </div>
  );
}

export default App;
