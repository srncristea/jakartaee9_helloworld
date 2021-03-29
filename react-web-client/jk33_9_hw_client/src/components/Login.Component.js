import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';

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
  email: '',
  password: '',
};

export default function Login () {
  const [user, setUser] = useState(initialState);
  const history = useHistory();

  function onInputChange (event) {
    let { name, value } = event.target;
    //console.log(`elem name: ${name} ; type: ${value}`);
    setUser({ ...user, [name]: value });
  }

  function onSubmit (event) {
    event.preventDefault();
    let { email, password } = user;
    userService
      .login(email, password)
      .then(response => {
        console.log(`Login reponse: ${JSON.stringify(response)}`);
        history.push('/success');
      })
      .catch(error => {
        console.error(`${error}`);
        history.push('/error');
      })
      .finally(() => setUser(initialState));
  }

  return (
    <div>
      <h2>User Login form</h2>
      <Form onSubmit={onSubmit}>
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
          Login
        </Button>
      </Form>
    </div>
  );
}
