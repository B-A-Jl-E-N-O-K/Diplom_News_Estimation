import Tree from '../tree.js'
import Sidepalte from '../sidebar.js'
import { useState } from 'react';
import Footer from '../footer.js';

export default function MainRender(props) {

    const [bartype, setBartype] = useState("birj");
    const [barinfo, setBarinfo] = useState("no data");

    function handleChangeBarType(e) {
      setBartype(e.target.type);
      setBarinfo(e.target.value);
    }

    if(!props.birja){
        return (
      <div className="App">
        <p>Data not found</p>
      </div>
    );
    }
    else{
        const birja = props.birja;
    }

    

    return (
      <div className="App">
        <div className="main">
          <div className="graph">
            <Tree birja={this.birja} onClick={handleChangeBarType} />
          </div>
          <div className="footer">
            <Footer bartype={bartype} barinfo={barinfo} />
          </div>
        </div>
        <div className="sideplate">       
          <Sidepalte bartype={bartype} barinfo={barinfo} />
        </div>
        
        
      </div>
    );
}