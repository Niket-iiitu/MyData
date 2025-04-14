import { API_BASE_URL } from '../config/Constants';

export async function getPing() {
  try {
    const response = await fetch(`${API_BASE_URL}/ping`);
    var res = await response.text()
    console.log(res);
    return true;
  } catch (error) {
    console.error('Ping failed:', error);
    return false;
  }
}
