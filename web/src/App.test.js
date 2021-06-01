import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BrowserRouter as Router } from 'react-router-dom';
import App from './App';
import Home from './pages/home'
import About from './pages/about'
import Login from './pages/login'
import Register from './pages/register'

test('Initial test', () => {
  render(<App />);
  screen.debug();
});

test('Upload file', () => {
  const file = new File(['hello'], 'hello.png', { type: 'image/png' })
 
  render(
    <div>
      <label htmlFor="file-uploader">Upload file:</label>
      <input id="file-uploader" type="file" />
    </div>
  )
  const input = screen.getByLabelText(/upload file/i)
  userEvent.upload(input, file)

  expect(input.files[0]).toStrictEqual(file)
  expect(input.files.item(0)).toStrictEqual(file)
  expect(input.files).toHaveLength(1)
});

test('Login page', () => {
  render(<Router>
            <Login />
        </Router>
        );
  screen.debug();
});

test('Register page', () => {
  render(<Router>
          <Register />
        </Router>
         );
  screen.debug();
});

test('Home page', () => {
  render(<Router>
          <Home />
        </Router>
         );
  screen.debug();
});

test('About page', () => {
  render(<Router>
          <About />
        </Router>
         );
  screen.debug();
});
