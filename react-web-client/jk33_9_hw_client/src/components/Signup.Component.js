import React, { useState } from 'react';

import {
  Form,
  FormControl,
  FormGroup,
  FormLabel,
  Button,
  Row,
} from 'react-bootstrap';

import userService from '../services/User.Service';

const initialState = {
  firstname: '',
  lastname: '',
  email: '',
  password: '',
};

export default function Signup ({ match, location, history }) {
  const [user, setUser] = useState(initialState);

  function onInputChange (event) {
    let { name, value } = event.target;
    setUser({ ...user, [name]: value });
  }

  function onSubmit (event) {
    event.preventDefault();
    userService
      .createAccount(user)
      .then(response => {
        console.log(`create accout response: ${JSON.stringify(response)}`);
        history.push('/login');
      })
      .catch(error => {
        console.error(`${error}`);
        history.push('/error');
      })
      .finally(() => {
        setUser(initialState);
      });
  }

  return (
    <>
      <h2>User Account creation</h2>
      <Form onSubmit={onSubmit}>
        <FormGroup as={Row} controlId='firstname-id'>
          <FormLabel>first name</FormLabel>
          <FormControl
            type='text'
            name='firstname'
            onChange={onInputChange}
            value={user.firstname}
            defaultValue='first name'
          />
        </FormGroup>
        <FormGroup as={Row} controlId='lastname-id'>
          <FormLabel>last name</FormLabel>
          <FormControl
            type='text'
            name='lastname'
            onChange={onInputChange}
            value={user.lastname}
            defaultValue='last name'
          />
        </FormGroup>
        <FormGroup controlId='email-id'>
          <FormLabel>Email</FormLabel>
          <FormControl
            type='email'
            name='email'
            onChange={onInputChange}
            value={user.email}
            defaultValue='email@example.com'
          />
        </FormGroup>
        <FormGroup controlId='password-id'>
          <FormLabel>Password</FormLabel>
          <FormControl
            type='password'
            name='password'
            onChange={onInputChange}
            value={user.password}
            defaultValue='password'
          />
        </FormGroup>
        <Button type='submit' variant='primary'>
          Create Account
        </Button>
      </Form>
    </>
  );
}
