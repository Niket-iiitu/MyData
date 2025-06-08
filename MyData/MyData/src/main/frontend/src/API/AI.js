import { API_BASE_URL } from '../config/Constants';

export async function generateSummery(data) {
  try {
    const response = await fetch(`${API_BASE_URL}/summarise`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'uid': localStorage.getItem('IdeaNotesUid'),
        'session-id': localStorage.getItem('IdeaNotesSessionId')
      },
      body: JSON.stringify({ data }),
    });

    if (!response.ok) {
      throw new Error('Failed to generate summary');
    }

    const res = await response.json();

    if (res.status === "SUCCESS") {
      return res.data;
    } else if (res.status === "ERROR") {
      throw new Error(res.errorMessage || 'Server returned an error');
    } else {
      throw new Error('Unexpected error occurred. Please try again later.');
    }

  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}