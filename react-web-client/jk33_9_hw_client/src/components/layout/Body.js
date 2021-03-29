import React from 'react';
import { Row, Col } from 'react-bootstrap';

export default function Body (children) {
  return (
    <Row id='body-row'>
      <Col id='body-col'>{children}</Col>
    </Row>
  );
}
