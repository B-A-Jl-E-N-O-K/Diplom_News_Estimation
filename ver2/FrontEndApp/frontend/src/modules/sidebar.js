import { Component } from 'react';


export default function Sidepalte(props) {

    const type = props.bartype;
    const info = props.barinfo;
    const securities = props.sec; 
    // console.log(birja)
    // console.log(ind)
    
    if (type === 'birja'){
            return (
                
                <div className="sideList">
                    
                    <div className="header">
                        <h2>{info.name}</h2>
                    </div>
                    <div className="content">
                        <p>Месторасположение: {info.descr}</p>
                        <p>Название: {info.stock}</p>
                        <p>Раздел: {info.regime}</p>
                        <p>Режим торгов: {info.region}</p>
                    </div>
                </div>
            );
        }
        else if(type === 'ind'){
            return (
                
                <div className="sideList">
                    
                    <div className="header">
                        <h2>{info.namerus}</h2>
                    </div>
                    <div className="content">
                        <p>Описание: {info.descr}</p> 
                    </div>  
                    <div className="secList">
                        <div className="secHeader">
                            <h3>Инвестиционные инструменты</h3>
                        </div>
                        {securities.length > 0 && securities.map(sec =>
                            <div className='secListItem' onClick={() => props.onClick('sec', sec)}>
                                Код: {sec.code}<br/>
                                Название: {sec.name}
                            </div>
                        )}
                    </div>
                </div>
            );
        }
        else{
            return (
                
                <div className="sideList">
                    
                    <div className="header">
                        <h2>{info.code}</h2>
                    </div>
                    <div className="content">
                        <p>Название: {info.name}</p>
                    </div>
                </div>
            );
        }
}