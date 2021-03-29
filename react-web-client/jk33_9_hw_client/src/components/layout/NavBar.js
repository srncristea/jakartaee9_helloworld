import React from 'react';

import { Navbar, Nav, Form, FormControl, Button } from 'react-bootstrap';

export default function NavigationBar (props) {
  return (
    <Navbar bg='primary' variant='dark'>
      <Navbar.Brand href='/'>JK33_9_HW</Navbar.Brand>
      <Nav className='mr-auto'>
        <Nav.Link href='/'>Home</Nav.Link>
        <Nav.Link href='/login'>Login</Nav.Link>
        <Nav.Link href='/signup'>SignUp</Nav.Link>
        <Nav.Link href='/users'>Users</Nav.Link>
        <Nav.Link href='/logout'>Logout</Nav.Link>
      </Nav>
      <Form inline>
        <FormControl
          type='text'
          placeholder='Search Users'
          className='mr-sm-2'
        />
        <Button variant='outline-light'>Search</Button>
      </Form>
    </Navbar>
  );
}
