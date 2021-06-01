
var sessionId = localStorage.getItem('sessionId');
if (sessionId === null || sessionId === 'null' || sessionId === '' || sessionId === undefined) {
  window.location.href = '/login.html';
}
