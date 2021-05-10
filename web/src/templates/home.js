import React from 'react'
import { Link } from 'react-router-dom'

import cover from '../assets/img/cover2.jpg';
import cover2 from '../assets/img/cover.jpg';
import avatar from '../assets/icon/avatar.png';
import adopt from '../assets/icon/adopt.png';
import done from '../assets/icon/done.png';

const Panel = ({id, name, image, price}) => (
    <article>
        <div className="relative h-56 rounded">
            <img
                alt={name}
                className="h-full rounded bg-black object-cover w-full"
                src={image}
            />
            <div className="absolute top-0 left-0 w-full h-full rounded transition-opacity duration-500 ease-in-out opacity-0 hover:opacity-100 flex justify-center items-center bg-opacity-40 bg-gray-800">
                <Link to={`/products/${id}`} className="cursor-pointer relative w-10 h-10 text-white rounded-full bg-red-500 p-2.5">
                    <svg stroke="currentColor" fill="currentColor" strokeWidth="0" viewBox="0 0 512 512"><path d="M505 442.7L405.3 343c-4.5-4.5-10.6-7-17-7H372c27.6-35.3 44-79.7 44-128C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c48.3 0 92.7-16.4 128-44v16.3c0 6.4 2.5 12.5 7 17l99.7 99.7c9.4 9.4 24.6 9.4 33.9 0l28.3-28.3c9.4-9.4 9.4-24.6.1-34zM208 336c-70.7 0-128-57.2-128-128 0-70.7 57.2-128 128-128 70.7 0 128 57.2 128 128 0 70.7-57.2 128-128 128z"></path></svg>
                </Link>
            </div>
        </div>
        <footer className="flex justify-between items-center mt-4 capitalize">
            <h6 className="tracking-wider">{name}</h6>
            <p className="text-red-500 tracking-widest">{price.toCurrency()}</p>
        </footer>
    </article>
)

const ServiceBox = ({title, children, content}) => (
    <div className="flex flex-col bg-red-600 px-8 py-10 gap-4 rounded-md justify-center items-center">
        <div className="relative mx-auto w-16 h-16 rounded-full bg-red-400 flex justify-center items-center p-2.5">
            {children} 
        </div>
        <div>
            <h4 className="font-semibold">{title}</h4>
            <p className="mt-2 leading-7 w-full text-sm md:text-base lg:text-lg">
                {content}
            </p>
        </div>
    </div>
)

class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          name: '',
          email: '',
          content: '',
        };
    
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const value = event.target.value;
        this.setState({
            ...this.state,
            [event.target.name]: value
        });
    }

    handleSubmit(event) {
        alert('A name was submitted: ' + this.state.name + this.state.email + this.state.content);
        event.preventDefault();
    }

    render() {
        return (
        <>
            <section id="showcase" className="py-16">
                <div className="tw-container grid lg:grid-cols-2 place-items-center gap-x-32">
                    <article>
                        <h1 className="tracking-wider font-bold text-4xl md:text-5xl">
                            For the stray care
                        </h1>
                        <p className="mt-8 max-w-lg leading-loose ">
                            Want a new friend to play, walk or run with? 
                            Are you ready to open the door of your home for a little one? 
                            Adopt a pet and enjoy with your new best friend.
                        </p>
                        <Link to="/products" className="btn px-6 py-3 bg-red-500 text-white w-full md:w-max text-center mt-8">Adopt Now</Link>
                    </article>
                    <article id="showcase-image" className="relative hidden lg:block">
                        <img 
                            alt="dining-room-example"
                            style={{
                                height: '550px',
                            }}
                            className="object-cover bg-gray-100 w-full rounded-md"
                            src={cover}
                        />
                        <img
                            alt="media-shelves-room"
                            className="z-30 bg-gray-100 transform -translate-x-2/4 absolute left-0 bottom-0  w-5/12 h-40 rounded-md"
                            src={cover2}
                        />
                    </article>
                </div>
            </section>
            <section id="featured-products" className="bg-gray-100">
                <div className="tw-container py-14">
                    <div>
                        <h2 className="text-center font-bold text-3xl lg:text-4xl">Breeds</h2>
                        <div className="mt-3 w-24 h-1 bg-red-400 mx-auto"/>
                    </div>
                   {/*  <div className="mt-16 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
                        <Panel
                            id="rec3jeKnhInKHJuz2"
                            name="vase table"
                            image="https://dl.airtable.com/.attachments/599ba19cf24d2114fea3c93d40c4e34c/46b486f1/vase-table.jpeg"
                            price={120999}
                        />
                        <Panel
                            id="rec7JInsuCEHgmaGe"
                            name="Utopia Sofa"
                            image="https://dl.airtable.com/.attachments/799a334af47039d9d0482f51f77675e9/bceadec0/product-3.jpg"
                            price={79999}
                        />
                        <Panel
                            id="recNZ0koOqEmilmoz"
                            name="Entertainment Center"
                            image="https://dl.airtable.com/.attachments/d42fd61c4d1ae2a02afe29114bd0fef3/d312dda5/product-2.jpg"
                            price={59999}
                        />
                    </div>
                    */}
                    <button className="btn-sm md:btn mt-16 mx-auto bg-red-500 text-red-50 font-semibold">View Pets</button>
                </div>
            </section>
            <section id="services" className="bg-red-400">
                <div className="transform py-20 xl:py-0 xl:translate-y-20 tw-container text-red-50">
                    <div className="grid grid-rows-2 lg:grid-rows-1 lg:grid-cols-2 items-center gap-y-4">
                        <h3 className="font-bold text-center text-white">Adoption process</h3>
                        <p className="leading-7 text-center">If you want to adopt a pet, take a look at the process that makes it possible below.</p>
                    </div>
                    <div className="mt-16 grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 text-center gap-10">
                        <ServiceBox title="Login!" content="The first step to adopting a pet is registration. If you already have an account, you need to log in.">
                            <img
                                alt="avatar"
                                src={avatar}
                            />
                        </ServiceBox>
                        <ServiceBox title="Choose a pet and click 'Adopt'." content="After signing up, select your new pet and click button 'Adopt'.">
                            <img
                                alt="adopt"
                                src={adopt}
                            />
                        </ServiceBox>
                        <ServiceBox title="Done! You have adopted a pet!" content="Congratulations on your new friendship.">
                            <img
                                alt="done"
                                src={done}
                            />
                        </ServiceBox>
                    </div>
                </div>
            </section>
            <section id="newsletter">
                <div className="tw-container my-16 md:my-32 lg:my-64">
                    <div className="grid grid-rows-2 lg:grid-rows-1 lg:grid-cols-2 items-center w-full">
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2876.9594191241113!2d18.39618041534269!3d43.85666977911483!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4758c923eeaa57a3%3A0x5cbfa583cac7da02!2sFaculty%20of%20Electrical%20Engineering!5e0!3m2!1sen!2sba!4v1620425092701!5m2!1sen!2sba" className="w-full h-full" width="600" height="450"  allowFullScreen="" title="1" loading="lazy"></iframe>    
                    <form className="m-auto mt-6 md:mt-0 grid grid-cols-1 w-full max-w-lg text-base md:text-lg lg:text-xl" style={{gridTemplateColumns: '1fr auto'}}>
                        <h2 className="text-left">Have a question?</h2>
                        <p className="text-left pt-5">In case you have any questions, contact us.</p>
                        <input type="text" name="name" value={this.state.name} onChange={this.handleChange} placeholder="Name"/>
                        <br/>
                        <input type="email" name="email" value={this.state.email} onChange={this.handleChange} placeholder="Email"/>
                        <br/>
                        <textarea type="text" name="answer" value={this.state.content} onChange={this.handleChange} placeholder="Message"/>
                        <br/>
                        <button className="btn px-6 py-3 bg-red-500 text-white text-center margin-auto mt-8" type="submit">Submit</button>
                    </form>
                    </div>
                </div>
            </section>
        </>
        )
    }

}

export default Home