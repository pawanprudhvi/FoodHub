import "./login.css"
const Login = () => {
    return (<div className='container'>
        <div className="header">
            <div className="headertext">
                Login/SignUp</div>
        </div>
        <div className="inputs">
            <input type="text" name="Email" placeholder="Email"></input><br />
            <button  className="loginbutton" name="Email" >Receive OTP</button>
        </div>
    </div>);
};
export default Login;