import React from 'react';

import { Jumbotron, Row, Col, Button } from 'react-bootstrap';

export default function Home () {
  return (
    <Jumbotron>
      <h2>Home Page</h2>
      <Row>
        <Col></Col>
        <Col></Col>
      </Row>
      <Row>
        <Button type=''>Start Application</Button>
      </Row>
    </Jumbotron>
  );
}
