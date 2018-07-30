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
        <Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} />
        <div style={{textAlign: 'center'}}>
          <h1 style={{color: 'blue', textAlign: 'left' , marginLeft: '28%' }}>服务概述</h1>
          <br />
          <p style={{textAlign: 'left' , marginLeft: '28%',textIndent: '2em',width:'45%',fontSize:'14pt' }}>安全是云服务发展及普及的关键要素，于是2015年4月国家颁布了GB/T 31167-2014《信息安全技术 云计算服务安全指南》和GB/T 31168-2014《信息安全技术 云计算服务安全能力要求》两个旨在评估云服务安全性的国家标准。依据这两个标准，孝道科技TCSEC团队结合多年安全集成服务的经验，为用户提供云环境下的“云安全差距分析”、“控制措施完整性评估”和“业务影响度分析”等众多云安全咨询服务。</p>
          <br />
          <h1 style={{color: 'blue', textAlign: 'left' , marginLeft: '28%' }}>测试内容</h1>
          <p>孝道科技TCSEC团队帮助企业专有云或公有云服务提供商对安全性进行中立的第三方安全评估，帮助企业在持续改进中不断的完善云安全控制体系，建立起符合国家标准和行业最佳实践的云安全控制体系。</p>
          <img src="/static/services/cloud.png"  />
          <img src="/static/services/cloud.jpg"  style={{ width:'1200px' }} />
        </div>
        
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />,
      </div>
    );
  }
}
