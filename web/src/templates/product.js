import React, { useState, useContext } from 'react'
import useFetch from '../hooks/useFetch'
import { useHistory, Link } from 'react-router-dom'
import { StoreContext } from '../context'

import Breadcrumbs from '../components/Breadcrumbs'
import ColorSelector from '../components/ColorSelector'
import Loader from '../components/Loader'
import Comment from '../components/Comment'

export default function Product({id}) {
    const {response: product, error } = useFetch(`https://course-api.com/react-store-single-product?id=${id}`)

    const history = useHistory();
    const { setCart } = useContext(StoreContext)

    const [imageIndex, setImageIndex] = useState(0)
    const [colorIndex, setColorIndex] = useState(0)
    const [quantity, setQuantity] = useState(1)

    const handleAdoptCart = () => {
        alert('A name was submitted: ' + id);
     
    }

    if(!product) return <Loader/>
    if(error) return <div>Error.</div>

    const { 
        stock, price, colors, 
        images, reviews, stars, 
        name, description, company
    } = product;

    const roundedStarNumber = Math.round(stars);
    const filledStar = <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
    const emptyStar = <path fillRule="evenodd" d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.523-3.356c.329-.314.158-.888-.283-.95l-4.898-.696L8.465.792a.513.513 0 00-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767l-3.686 1.894.694-3.957a.565.565 0 00-.163-.505L1.71 6.745l4.052-.576a.525.525 0 00.393-.288l1.847-3.658 1.846 3.658a.525.525 0 00.393.288l4.052.575-2.906 2.77a.564.564 0 00-.163.506l.694 3.957-3.686-1.894a.503.503 0 00-.461 0z" clipRule="evenodd"></path>

    return(
        <>
            <Breadcrumbs>
                <Link to='/'>Home</Link>
                <Link to='/products'>Products</Link>
                <span>{name}</span>
            </Breadcrumbs>
            <section>
                <div className="tw-container py-16">
                    <Link to="/products" className="btn-sm bg-red-500 text-white w-max text-uppercase">Back</Link>
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
                                    {/*stock > 0 ? 'In Stock' : 'Out of Stock'*/}
                                    <span className="font-bold text-right">Dog</span>
                                    
                                </p>
                                <hr className="my-4 md:my-6"/>
                                <p className="grid grid-cols-2">
                                    <span>Breed: </span>
                                    <span className="font-bold text-right">German Sheperd</span> 
                                </p>
                                <hr className="my-4 md:my-6"/>
                                <p className="grid grid-cols-2">
                                    <span>Location: </span>
                                    <span className="font-bold text-right">Sarajevo</span> 
                                </p>
                                <hr className="my-4 md:my-6"/>
                                <p className="grid grid-cols-2">
                                    <span>Age: </span>
                                    <span className="font-bold text-right">2</span>
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
                                alt={images[imageIndex].name}
                                className="main-product-image-sm md:main-product-image-md lg:main-product-image bg-gray-300 rounded object-cover w-full"
                                src={images[imageIndex].url}
                            />
                            <div className="mt-4 grid grid-cols-5 gap-4">
                            {
                                images.map((image, i) => (
                                    <img
                                        alt={image.name}
                                        key={image.id}
                                        className={`
                                            bg-gray-300
                                            h-12 md:h-20 w-full object-cover
                                            rounded cursor-pointer 
                                            ${imageIndex === i ? 'border-2 border-red-500' : ''}
                                        `}
                                        src={image.url}
                                        onClick={() => setImageIndex(i)}
                                    />
                                ))
                            }
                            </div>
                        </article>
                    </div>
                </div>
            </section>
            <section>
                <div>
                    <Comment></Comment>
                </div>
            </section>
        </>
    )
}