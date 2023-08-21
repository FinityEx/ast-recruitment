import ReactDOM from 'react-dom';
import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';

function FooterPortal({ children }) {
  const [el, setEl] = useState(null);

  useEffect(() => {
    setEl(document.getElementById('portal-footer'));
  }, []);

  if (!el) return null;

  return ReactDOM.createPortal(children, el);
}

FooterPortal.propTypes = {
  children: PropTypes.oneOfType([PropTypes.arrayOf(PropTypes.node), PropTypes.node]).isRequired
};

export default FooterPortal;
