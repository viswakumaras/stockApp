class UserService {
    /**
     * SignUp 
     * @param {*} username 
     * @param {*} password 
     * @param {*} email 
     */
  static async signUp(user,pass){

      // URL + Data
     
      return  axios.get('/api', {
        params: {
          username:user,
          password:pass
        }
      });
  }

  /**
   * login
   * @param {*} username 
   * @param {*} password 
   * @param {*} email 
   */
  static async login(user,pass){

    let res =   await  axios.get('http://localhost:8080/stockappApi/user/auth',{
        params: {
          username:user,
          password:pass
        }
      });
      
      if(res.data=="true"){
          return true;
      }
      else{
          return false;
      }
}

static async getUserId(user,pass){
  let res= await axios.get('http://localhost:8080/stockappApi/user/id', { params: {
    username:user,
    password:pass
  }
});
return await  res;
}

}