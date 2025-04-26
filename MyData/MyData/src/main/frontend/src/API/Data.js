import { API_BASE_URL } from '../config/Constants';

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

export async function fetchCategories() {
  try {
    const response = await fetch(`${API_BASE_URL}/fetchCategories`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: "",
    });

    if (!response.ok) {
      throw new Error('Failed to fetch notes');
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

export async function updateNote(note) {
  try {
    const response = await fetch(`${API_BASE_URL}/createAndUpdateNote`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(note),
    });

    if (!response.ok) {
      throw new Error('Failed to update note');
    }

    const res = await response.json();

    if(res.status === "SUCCESS"){
        return "Note Updated Successfully!";
    }
    else if(res.status === "ERROR"){
        return (res.errorMessage || 'Server returned an error');
    }
    else{
        return 'Unexpected error occurred. Please try again later.';
    }

  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}

export async function createNote(note) {
  try {
    const response = await fetch(`${API_BASE_URL}/createAndUpdateNote`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(note),
    });

    if (!response.ok) {
      throw new Error('Failed to create note');
    }

    const res = await response.json();

    if(res.status === "SUCCESS"){
        return "Note Created Successfully!";
    }
    else if(res.status === "ERROR"){
        return (res.errorMessage || 'Server returned an error');
    }
    else{
        return 'Unexpected error occurred. Please try again later.';
    }

  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}

export async function deleteNote(noteId) {
  try {
    const response = await fetch(`${API_BASE_URL}/deleteNote`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ noteId }),
    });

    if (!response.ok) {
      throw new Error('Failed to delete note');
    }

    const res = await response.json();

    if (res.status === "SUCCESS") {
      return "Note Deleted Successfully!";
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