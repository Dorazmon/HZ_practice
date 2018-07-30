import React from 'react'
import ReactDOM from 'react-dom'
import enquire from 'enquire.js'
import { scrollScreen } from 'rc-scroll-anim'
import { BackTop, Affix, Row, Col } from 'antd' 
import Nav from '../../components/Nav'
import Footer from '../../components/Footer'

export default class User extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isMode: false
    };
  }

  componentDidMount() {
    // 适配手机屏幕;
    this.enquireScreen((isMode) => {
      this.setState({ isMode });
    });
  }

  enquireScreen = (cb) => {
    /* eslint-disable no-unused-expressions */
    enquire.register('only screen and (min-width: 320px) and (max-width: 767px)', {
      match: () => {
        cb && cb(true);
      },
      unmatch: () => {
        cb && cb();
      },
    });
    /* eslint-enable no-unused-expressions */
  }

  render() {
    return (
      <div className="templates-wrapper">
        <Affix><Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} /></Affix>,
        <div style={{textAlign: 'center'}}>
        <h1 style={{color: 'blue', textAlign: 'left' , marginLeft: '28%' }}>企业简介</h1>
          <br />
          <p style={{textAlign: 'left' , marginLeft: '28%',textIndent: '2em',width:'45%',fontSize:'14pt' }}>杭州孝道科技有限公司（简称：孝道科技）座落在美丽的西子湖畔，位于中国（杭州）智慧信息产业园区，公司创立于2014年，由业内领先安全公司、阿里资深专家、海外高层次留学归国专家及业内知名企业的CTO等团队共同创办。孝道科技一直致力于信息安全技术的研究、实践和积累，专注为用户提供云安全、移动安全、大数据安全及IOT安全的产品和服务，立志成为业界最具有影响力的综合信息安全解决方案提供商。</p>
          <br />
          <p style={{textAlign: 'left' , marginLeft: '28%',textIndent: '2em',width:'45%',fontSize:'14pt' }}>孝道科技目前已服务于全国百余家大型制造企业，包括传化集团、物产集团、兴合集团、海尔集团、航天科工集团、义乌小商品城、海康威视、中策集团及通策集团等大型企业；同时已覆盖了全省的海尔快捷通、甬易支付、商盟商务、连连支付、网易宝、贝付科技、传化金服、航天电子、义乌小商品城支付、网盛生意宝等十多家支付公司，另外还涵盖了各大互联网消费金融和互联网电商企业，包括鑫合汇、爱学贷、点点搜财、永达互联网金融、小拇指、什么值得买、东海商品、泰一指尚等知名企业。</p>
          <br />
          <h1 style={{color: 'blue', textAlign: 'left' , marginLeft: '28%' }}>公司使命</h1>
          <br />
          <p style={{textAlign: 'left' , marginLeft: '28%',textIndent: '2em',width:'45%',fontSize:'14pt' }}>让世界互联更安全</p>
          <br />
          <h1 style={{color: 'blue', textAlign: 'left' , marginLeft: '28%' }}>企业价值观</h1>
          <br />
          <p style={{textAlign: 'left' , marginLeft: '28%',textIndent: '2em',width:'45%',fontSize:'14pt' }}>学习、创新、坚持、梦想</p>
          <p style={{textAlign: 'left' , marginLeft: '28%',textIndent: '2em',width:'45%',fontSize:'14pt' }}>诚实、守信、合作、共赢</p>      
          <br />
          <img src="/static/about.jpg"  style={{ width:'1200px' }} />
        </div>
        
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />,
      </div>
    );
  }
}
