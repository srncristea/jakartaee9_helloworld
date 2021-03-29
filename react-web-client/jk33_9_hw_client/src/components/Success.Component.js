import React, { useState } from 'react';
import { useHistory, useLocation, useParams } from 'react-router-dom';
import { Modal, Button } from 'react-bootstrap';

export const Success = props => {
  const [show, setShow] = useState(true);

  const history = useHistory();
  const location = useLocation();
  const params = useParams();

  function handleClose () {
    setShow(false);
    // console.log(`location state: ${JSON.stringify(location.state())}`);
    // console.log(`params: ${JSON.stringify(params)}`);

    history.push('/');
  }

  return (
    <>
      <Modal
        show={show}
        onHide={handleClose}
        backdrop='static'
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>Operation with Success</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          I will not close if you click outside me. Don't even try to press
          escape key.
        </Modal.Body>
        <Modal.Footer>
          <Button variant='secondary' onClick={handleClose}>
            Close
          </Button>
          <Button variant='primary'>Understood</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
