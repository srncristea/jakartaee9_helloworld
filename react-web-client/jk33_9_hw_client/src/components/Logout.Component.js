import React from 'react';

import { Row, Col, Jumbotron, Button } from 'react-bootstrap';
import userService from '../services/User.Service';

export default function Logout (token) {
  function logout (token) {
    userService.logout(token);
  }
  return (
    <Jumbotron>
      <p>Are you sure you want to logout</p>
      <Button onClick={logout}>Logout</Button>
    </Jumbotron>
  );
}
