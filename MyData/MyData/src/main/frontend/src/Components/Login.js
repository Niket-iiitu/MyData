import React, { useState, useEffect } from 'react';
import './Login.css';
import {signUp, logIn, autoLogin} from '../API/Authentication.js'

function Login({ onLogin }) {
    const [isSignup, setIsSignup] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');

    useEffect(() => {
        if(localStorage.getItem('IdeaNotesUid') && localStorage.getItem('IdeaNotesSessionId')){
                autoLogin(localStorage.getItem('IdeaNotesUid'), localStorage.getItem('IdeaNotesSessionId')).then((res)=>{
                    if(res.status === "AUTHORISED"){
                        onLogin();
                    }
                    else{
                        localStorage.removeItem('IdeaNotesUid');
                        localStorage.removeItem('IdeaNotesSessionId');
                    }
                }).catch(err => {
                    localStorage.removeItem('IdeaNotesUid');
                    localStorage.removeItem('IdeaNotesSessionId');
                });
            }
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if(isSignup){
            signUp(email, password, name).then((res) => {
                if(res.status === "REGISTERED"){
                    setIsSignup(false);
                    setEmail("");
                    setName("");
                    setPassword("");
                }
                else{
                    alert(res.errorMessage || 'Registration failed');
                }
            }).catch(err => alert(err.message));
        }
        else{
            logIn(email, password).then((res)=>{
                if(res.status === "AUTHORISED"){
                    localStorage.setItem('IdeaNotesUid', res.uid);
                    localStorage.setItem('IdeaNotesSessionId', res.sessionId);
                    onLogin();
                }
                else{
                    alert(res.errorMessage || 'Invalid credentials');
                }
            }).catch(err => alert(err.message));
        }
    };

    return (
        <div className="login-container">
            <h2>{isSignup ? 'Sign Up' : 'Login'}</h2>
            <form onSubmit={handleSubmit} className="login-form">
                {isSignup && (
                    <div className="form-group">
                        <label>Name:</label>
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </div>
                )}
                <div className="form-group">
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="submit-button">
                    {isSignup ? 'Sign Up' : 'Login'}
                </button>
            </form>
            <button onClick={() => setIsSignup(!isSignup)} className="toggle-button">
                {isSignup ? 'Already have an account? Login' : 'Don\'t have an account? Sign Up'}
            </button>
        </div>
    );
}

export default Login;