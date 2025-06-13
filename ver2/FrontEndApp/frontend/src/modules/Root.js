import MainBlock from './MainBlock.js'

export default async function Root() {

  const birja = await componentDidMount()
  const ind_tree = await getIndForTree()
  

  return (<MainBlock birja={birja} industries={ind_tree}/>);

}

async function componentDidMount() {
    const response = await fetch('http://localhost:8080/api/source');
    const string = await response.text();
    // console.log(string)
    const json = string === "" ? {} : JSON.parse(string);
    // const body = await response.json();
    // console.log(json)
    return json;
    
  }

async function getIndForTree() {
  const response = await fetch('http://localhost:8080/api/industry');
  const string = await response.text();
  const json = string === "" ? {} : JSON.parse(string);
  // console.log(json)
  return json;
}