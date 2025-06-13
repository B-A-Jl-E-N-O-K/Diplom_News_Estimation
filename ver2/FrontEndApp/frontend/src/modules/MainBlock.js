import Tree from './tree.js'
import Sidepalte from './sidebar.js'
import Footer from './footer.js'
import { useState } from 'react';

export default function MainBlock(props) {

    const birja = props.birja
    const ind_tree = props.industries
    const sec = props.securities
    const news = props.news

    const [bartype, setBartype] = useState("birj");
    const [barinfo, setBarinfo] = useState(birja);
    
    function handleChangeBarType(e) {
        setBartype(e.target.type);
        setBarinfo(e.target.value);
    }

    let cur_news;
    if(bartype === "birj"){
        cur_news=news.slice(0,2)
    }
    else{
        cur_news=news.slice(2,3)
    }


    
    return (
            <div className="App">
            <div className="main">
                <div className="graph">
                <Tree birja={birja} ind={ind_tree} onClick={handleChangeBarType} />
                </div>
                <div className="footer">
                {<Footer bartype={bartype} barinfo={barinfo} news={cur_news}/>}
                </div>
            </div>
            <div className="sideplate">       
                {<Sidepalte bartype={bartype} barinfo={barinfo} sec={sec}/>}
            </div>
            </div>
        );

}
