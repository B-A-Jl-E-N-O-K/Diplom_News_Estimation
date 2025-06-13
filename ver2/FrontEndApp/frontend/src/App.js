import Tree from './modules/tree.js'
import Sidepalte from './modules/sidebar.js'
import Footer from './modules/footer.js'
import { useState } from 'react';


export default function App(props) {

  
    const birja = props.birja
    const ind = props.ind
    const sec = props.sec
    const news = props.news

    const [bartype, setBartype] = useState("birja");
    const [barinfo, setBarinfo] = useState(birja);
    
    function handleChangeBarType(type, value) {
        setBartype(type);
        setBarinfo(value);
        // console.log(type)
        // console.log(value)
    }

    var cur_sec;
    if (bartype == "ind" || bartype == "sec"){
        cur_sec = sec.filter(function (el) {return el.indname == barinfo.name});
    }
    else{
        cur_sec = null;
    }

    var cur_news;
    if (bartype == "ind"){
        cur_news = news.filter(function (el) {return el.secname == barinfo.name});
    }
    else if (bartype == "sec"){
        cur_news = news.filter(function (el) {return el.secname == barinfo.code});
    }
    else{
        cur_news = news.filter(function (el) {return el.secname == null});
    }
    console.log(cur_news)


    return (
            <div className="App">
            <div className="main">
                <div className="graph noselect">
                <Tree bartype={bartype} barinfo={barinfo} birja={birja} ind={ind} news={cur_news} onClick={handleChangeBarType} />
                </div>
                <div className="footer">
                {<Footer news={cur_news}/>}
                
                </div>
            </div>
            <div className="sideplate noselect">       
                {<Sidepalte bartype={bartype} barinfo={barinfo} sec={cur_sec} onClick={handleChangeBarType}/>}
            </div>
            </div>
        );
  
}
