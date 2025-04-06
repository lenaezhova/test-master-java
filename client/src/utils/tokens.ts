export const getAccessToken = () => {
  return localStorage.getItem('access-token')
}

export const getRefreshToken = () => {
  return localStorage.getItem('refresh-token')
}

export const removeAccessToken = () => {
  return localStorage.removeItem('access-token')
}

export const removeRefreshToken = () => {
  return localStorage.removeItem('refresh-token')
}

export const setAccessToken = (token) => {
  return localStorage.setItem('access-token', token)
}

export const setRefreshToken = (token) => {
  return localStorage.setItem('refresh-token', token)
}
