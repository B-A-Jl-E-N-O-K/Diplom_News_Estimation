import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App.js';
// import reportWebVitals from './reportWebVitals';
// import 'bootstrap/dist/css/bootstrap.min.css';

// Боковая панель для акции, нижняя граница для хедера дерева, убрать тип имени компании новости?,
// алгоритм добавления новости и преобразования в дао
// картинка вместо слова для оценки и результат по факту (добвать колонку в новости класс)
const birja = await getSource();
const ind = await getInd();
const sec = await getSec();
const news = await getNews();


// const news=[
//   {id: 1, secname: "MOEX", link: "https://www.kommersant.ru/doc/7733685", title:"Здоровье в плановом порядке", est: true, time:"2025-05-17 00:31", res: 1},
//   {id: 2, secname: "finance",link: "https://www.kommersant.ru/doc/7715467", title:"Разгон на двух колесах", est: false, time:"2025-05-16 10:13", res: 0},
//   {id: 3, secname: "AFLT",link: "https://www.kommersant.ru/doc/7734140", title:"Moody's понизило долгосрочный кредитный рейтинг США", est: false, time:"2025-05-17 02:46", res: null}];
  

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App birja={birja} ind={ind} sec={sec} news={news}/> 
    
  </React.StrictMode>
);

async function getSource() {
    const response = await fetch('http://localhost:8080/api/source');
    const string = await response.text();
    // console.log(string)
    const json = string === "" ? {} : JSON.parse(string);
    // const body = await response.json();
    // console.log(json)
    return json;
    
  }

async function getInd() {
  const response = await fetch('http://localhost:8080/api/industry');
  const string = await response.text();
  const json = string === "" ? {} : JSON.parse(string);
  // console.log(json)
  return json;
}

async function getSec() {
  const response = await fetch('http://localhost:8080/api/security');
  const string = await response.text();
  const json = string === "" ? {} : JSON.parse(string);
  // console.log(json)
  return json;
}

async function getNews() {
  const response = await fetch('http://localhost:8080/api/news');
  const string = await response.text();
  const json = string === "" ? {} : JSON.parse(string);
  console.log(json)
  return json;
}

// async function getInfoFooterSource() {
//   const response = await fetch('http://localhost:8080/api/news/source');
//   const string = await response.text();
//   const json = string === "" ? {} : JSON.parse(string);
//   // console.log(json)
//   return json;
//         if(this.props.bartype === 'ind'){
//             const response = await fetch(`/api/news/industry/${this.props.barinfo.name}`);
//             const body = await response.json();
//             this.setState({news: body});
//         }
//         else if(this.props.bartype === 'birj'){
//             const response = await fetch(`/api/news/source`);
//             const body = await response.json();
//             this.setState({news: body});
//         }
        
// }





// // If you want to start measuring performance in your app, pass a function
// // to log results (for example: reportWebVitals(console.log))
// // or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
