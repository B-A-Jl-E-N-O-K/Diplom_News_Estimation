
export default function Footer(props) {

    const news = props.news;

    return (
            
        <div className="footerSecList">

            <table className="footTable">
                <tr><th>Ссылка</th><th>Новость</th><th>Время появления</th><th>Оценка системы</th><th>Результат</th></tr>
                {news.length > 0 && news.map(item =>
                        <tr className="footRow" key={item.id}>
                            <td><a href={item.link}>Ссылка</a></td>
                            <td>{item.title}</td>
                            <td>{item.time}</td>
                            <td><img src="arrow.svg" className={(item.est === 'true') ? "upNews" : "downNews"}></img></td>
                            <td><img src="question-svgrepo-com.svg" className="noDataNews"></img></td>
                            {/* {item.res === null &&
                                <td><img src="question-svgrepo-com.svg" className="noDataNews"></img></td>
                            } 
                            {item.res !== null &&
                                <td><img src="growth-svgrepo-com.svg" className={(item.res === 1) ? "upNews" : "downNews"}></img></td>
                            }  */}
                        </tr>
                )}
            </table>
        </div>
    );
}