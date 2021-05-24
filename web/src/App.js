import React from 'react'
import './App.css'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from 'react-router-dom'

import AppProvider from './components/AppProvider'


import Home from './templates/home'
import Products from './templates/products'
import Product from './templates/product'
import About from './templates/about'
import Login from './templates/login'
import Register from './templates/register'
import Profile from './templates/profile'
import PasswordRecovery from './templates/passwordRecovery'
import Categories from './templates/categories'
import Rase from './templates/rase'
import RasePets from './templates/rasePets'
import Pet from './templates/pet'
import AllPets from './templates/allPets'

// eslint-disable-next-line no-extend-native
Number.prototype.toCurrency = function(){
  return `$${(this / 100).toFixed(2).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}`;
}


function App() {
  return (
    <Router>
      <AppProvider>
        <Switch>
          <Route exact path="/" component={Home}/>
          <Route exact path="/products">
            <Products/>
          </Route>
          <Route 
            path="/products/:id"
            render={(props) => <Product {...props} {...props.match.params}/>}
          />
          <Route 
            path="/rases/:id"
            render={(props) => <Rase {...props} {...props.match.params}/>}
          />
          <Route 
            path="/pets/inRase/:id"
            render={(props) => <RasePets {...props} {...props.match.params}/>}
          />
          <Route 
            path="/pet/:id"
            render={(props) => <Pet {...props} {...props.match.params}/>}
          />
          <Route path="/about" component={About}/>
          <Route path="/login" component={Login}/>
          <Route path="/register" component={Register}/>
          <Route path="/profile" component={Profile}/>
          <Route path="/categories" component={Categories}/>
          <Route path="/password-recovery" component={PasswordRecovery}/>
          <Route path="/all-pets" component={AllPets}/>
          <Route path="*">
            <div className="tw-container text-center py-20">
              <h2 className="font-bold">404: Page Not Found</h2>
              <Link to="/" className="mt-5 btn w-max mx-auto">Return To Home</Link>
            </div>
          </Route>
        </Switch>
      </AppProvider>
    </Router>
  );
}

export default App;
