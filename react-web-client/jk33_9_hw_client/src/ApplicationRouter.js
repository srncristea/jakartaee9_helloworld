import React from 'react';
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';

// import Layout from './components/layout';

import Home from './components/Home.Component';
import Login from './components/Login.Component';
import Logout from './components/Logout.Component';
import Signup from './components/Signup.Component';
import ViewUSers from './components/ViewUsers.Component';

import { Success } from './components/Success.Component';
import { Error } from './components/Error.Component';

function ApplicationRouter () {
  return (
    <>
      {/* <Layout> */}
      <Router>
        <Switch>
          <Route path='/' component={Home} exact />
          <Route path='/login' component={Login} />
          <Route path='/logout' component={Logout} />
          <Route path='/signup' component={Signup} />
          <Route path='/users' component={ViewUSers} />

          {/* <Route path='/success' render={() => <div>List of Items</div>} />
          <Route path='/error' render={() => <div>List of Items</div>} /> */}
          <Route path='/success' component={Success} />
          <Route path='/error' component={Error} />
        </Switch>
      </Router>
      {/* </Layout> */}
    </>
  );
}

export default ApplicationRouter;
