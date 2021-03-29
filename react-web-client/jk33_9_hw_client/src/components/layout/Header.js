import React from 'react';
import { Jumbotron, Row, Col, Button } from 'react-bootstrap';

export default function Header (props) {
  return (
    <Jumbotron className='text-center'>
      <h1>Hello World</h1>
      <p>Jakarta EE 9 - Hello World Demo Application</p>
      <p>
        <Button
          href='https://react-bootstrap.github.io/'
          type='button'
          variant='primary'
        >
          Learn More
        </Button>
      </p>
    </Jumbotron>
  );
}
