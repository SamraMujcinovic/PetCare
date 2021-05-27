import React, { useState, useContext } from 'react'
import useFetch from '../hooks/useFetch'
import { useHistory, Link } from 'react-router-dom'

import Breadcrumbs from '../components/Breadcrumbs'
import Loader from '../components/Loader'
import axios from "axios";
import Comment from '../components/Comment'
import { getToken, getUser, logoutUser } from "../utilities/Common";
import {NotificationContainer, NotificationManager} from 'react-notifications';

export default function Pet({id}) {
    const {response: pet, error } = useFetch(`http://localhost:8088/pet_category_service_api/pet/${id}`)

    const history = useHistory();

    const handleAdoptCart = () => {
        axios.post(
            `http://localhost:8088/adopt_service_api/eurekaa/adoption-request/${id}`,
            {
              headers: {
                 Authorization: "Bearer " + getToken(),
              }, 
            }
          )
        .then((response) => {
            return NotificationManager.success(response.data.message, '  ', 3000);
        })
        .catch((error) => {
            return NotificationManager.error('Pet is not adopted', '  ', 3000);
        });
    }

    if(!pet) return <Loader/>
    if(error) return <div>Error.</div>

    const { 
        name, description
    } = pet;

    return(
        <>
            <Breadcrumbs>
                <Link to='/'>Home</Link>
                <Link to='/all-pets'>All pets</Link>
                <span>{name}</span>
            </Breadcrumbs>
            <button className="btn-sm bg-red-500 text-white w-max text-uppercase ml-20 mt-5" onClick={history.goBack}>Back</button>
            <section>
                <div className="tw-container py-16">
                   <div className="grid lg:grid-cols-2 items-center gap-16 mt-6">
                        <article id="product-info" className="capitalize flex flex-col gap-5 lg:gap-4 text-sm md:text-base">
                            <div>
                                <h2 className="font-bold">{name}</h2>
                            </div>
                            <p className="leading-loose">{description}</p>
                            <div className="w-full lg:w-full flex flex-col gap-0">
                                <hr className="my-4 md:my-6"/>
                                <p className="grid grid-cols-2">
                                    <span>Animal type: </span>
                                    <span className="font-bold text-right">{pet.rase.category.name}</span>
                                    
                                </p>
                                <hr className="my-4 md:my-6"/>
                                <p className="grid grid-cols-2">
                                    <span>Breed: </span>
                                    <span className="font-bold text-right">{pet.rase.name}</span> 
                                </p>
                                <hr className="my-4 md:my-6"/>
                                <p className="grid grid-cols-2">
                                    <span>Location: </span>
                                    <span className="font-bold text-right">{pet.location}</span> 
                                </p>
                                <hr className="my-4 md:my-6"/>
                                <p className="grid grid-cols-2">
                                    <span>Age: </span>
                                    <span className="font-bold text-right">{pet.age}</span>
                                </p>
                                <hr className="my-4 md:my-6"/>
                            </div>
                            {
                                <div class="adopt-div flex-row-reverse">
                                    <button 
                                        className="btn bg-red-500 text-white md:w-1/2 lg:w-max f-right"
                                        onClick={handleAdoptCart}
                                    >
                                        Adopt
                                    </button>
                                </div>
                            }
                        </article>
                        <article id="product-photos">
                            <img
                                alt={pet.image}
                                className="main-product-image-sm md:main-product-image-md lg:main-product-image bg-gray-300 rounded object-cover w-full"
                                //src={images[imageIndex].url}
                            />
                        </article>
                    </div>
                </div>
            </section>
            <section>
                <div>
                    <Comment id={id} category={2}></Comment>
                </div>
            </section>

            <NotificationContainer/>
        </>
    )
}