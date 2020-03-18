import React from 'react';
import './App.css';
import Person from './Person/Person';

function App() {
  return (
    <div className="App">
      <h1>Hi, This is React App</h1>
      <p>This is really working!!!!</p>
      <Person />
    </div>
  );
  // return React.createElement('div', {className: 'App'}, React.createElement('h1', null, 'Hi, this is react app!!!'));
}

export default App;
