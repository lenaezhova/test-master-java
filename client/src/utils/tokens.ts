export const getAccessToken = () => {
  return localStorage.getItem('access-token')
}

export const removeAccessToken = () => {
  return localStorage.removeItem('access-token')
}

export const setAccessToken = (token) => {
  return localStorage.setItem('access-token', token)
}
