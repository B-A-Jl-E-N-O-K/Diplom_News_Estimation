import { Component } from 'react';

export default function Tree(props) {

    const birja = props.birja;
    const ind= props.ind;
    const type = props.bartype;
    const info = props.barinfo;
    const news = props.news;

    let isRising = 0;
    news.forEach(function(s) {
    if (s.est){isRising += 1}
    else{isRising -= 1}
    });

    
    return(  
        <div className="tree">
            <div className="header" onClick={() => props.onClick('birja', birja)}>
                <div className="item">{birja.name}</div>
            </div>
            <div className="content">
                
                {ind.length > 0 && ind.map(ind =>
                    <div className="item" key={ind.id} onClick={() => props.onClick('ind', ind)}>
                        {type === "ind" && info.name === ind.name &&
                            <div className='treeNodeIns resblock'>
                                <p>{ind.namerus}</p>
                                {isRising > 0 && <img src="arrow.svg" className="upNews treeresimg"></img>}
                                {isRising < 0 && <img src="arrow.svg" className="downNews treeresimg"></img>}
                                {isRising == 0 && <img src="question-svgrepo-com.svg" className="treeresimg"></img>}
                                
                                
                            </div>
                        }
                        {info.name != ind.name &&
                            <div className={(type === "ind") ? "treeNodeIns notcur" : "treeNodeIns"}>
                                <img src={ind.name + ".svg"} className='treeimg'></img>
                            </div>
                        }
                        
                        
                    </div>
                )}
 
            </div>
        </div>
    )
}