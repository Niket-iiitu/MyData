import { API_BASE_URL } from '../config/constants';

export async function fetchTiles(filter = 'Default') {
  try {
    const response = await fetch(`${API_BASE_URL}/tileList`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ filter }),
    });

    if (!response.ok) {
      throw new Error('Failed to fetch tiles');
    }

    const res = await response.json();

    if(res.status === "SUCCESS"){
        return res.data;
    }
    else if(res.status === "ERROR"){
        throw new Error(res.errorMessage || 'Server returned an error');
    }
    else{
        throw new Error('Unexpected error occurred. Please try again later.');
    }

  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}
