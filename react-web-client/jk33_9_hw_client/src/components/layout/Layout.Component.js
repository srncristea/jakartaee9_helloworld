import React from 'react';

import { Container } from 'react-bootstrap';

import Header from './Header';
import Body from './Body';
import NavBar from './NavBar';
import Footer from './Footer';

const Layout = ({ children }) => {
  return (
    <>
      {Header()}
      <Container>
        {NavBar()}
        {Body(children)}
      </Container>
      {Footer()}
    </>
  );
};

export default Layout;
