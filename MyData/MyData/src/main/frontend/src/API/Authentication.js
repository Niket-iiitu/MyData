import { API_BASE_URL } from '../config/Constants';

export async function signUp(email, password, name) {
  try {
    const response = await fetch(`${API_BASE_URL}/signUp`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        "email": email,
        "password": password,
        "name": name
      }),
    });

    if (!response.ok) {
      throw new Error('Failed to Sign Up');
    }

    const res = await response.json();

    if (res.status === "SUCCESS") {
      return res.data;
    } else if (res.status === "ERROR") {
      return res.errorMessage || 'Server returned an error';
    } else {
      return 'Unexpected error occurred. Please try again later.';
    }

  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}

export async function logIn(email, password) {
  try {
    const response = await fetch(`${API_BASE_URL}/logIn`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        "email": email,
        "password": password
      }),
    });

    if (!response.ok) {
      throw new Error('Failed to Log In');
    }

    const res = await response.json();

    if (res.status === "SUCCESS") {
      return res.data;
    } else if (res.status === "ERROR") {
      return res.errorMessage || 'Server returned an error';
    } else {
      return 'Unexpected error occurred. Please try again later.';
    }

  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}

export async function autoLogin(uid, sessionId) {
  try {
    const response = await fetch(`${API_BASE_URL}/autoLogin`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        "uid": uid,
        "sessionId": sessionId
      }),
    });

    if (!response.ok) {
      throw new Error('Failed to Log In');
    }

    const res = await response.json();

    if (res.status === "SUCCESS") {
      return res.data;
    } else if (res.status === "ERROR") {
      return res.errorMessage || 'Server returned an error';
    } else {
      return 'Unexpected error occurred. Please try again later.';
    }

  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}