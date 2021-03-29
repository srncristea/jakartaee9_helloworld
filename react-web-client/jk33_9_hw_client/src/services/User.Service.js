const userService = {
  login: async (email, password) => {
    return new Promise((resolve, reject) => {
      setTimeout(() => resolve({ result: 'success' }), 1000);
    });
  },

  logout: async token => {
    console.log(`Logout Account: ${token}`);
    return new Promise((resolve, reject) => {
      setTimeout(() => resolve({ result: 'success' }), 1000);
    });
  },
  createAccount: async user => {
    console.log(`Create Account: ${JSON.stringify(user)}`);
    return new Promise((resolve, reject) => {
      setTimeout(() => resolve({ result: 'success' }), 1000);
    });
  },
};

export default userService;
