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

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}
