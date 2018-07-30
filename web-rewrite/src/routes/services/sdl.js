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
        <h1 style={{color: 'blue', textAlign: 'left' , marginLeft: '28%' }}>服务概述</h1>
          <br />
          <p style={{textAlign: 'left' , marginLeft: '28%',textIndent: '2em',width:'45%',fontSize:'14pt' }}>为用户建立全面的信息系统生命周期安全保障体系框架，实施保障体系可以为用户明确信息系统生命周期各阶段的各种安全保障流程、方法、活动，以及实施这些流程、方法、活动的组织机构建设和责任划分。企业在业务应用的开发过程中应该如何应对各种可能对应用造成影响的安全风险，安全开发生命周期流程将安全和隐私保护嵌入到软件系统有着至关重要的作用。用于安全设计和实施以及进行安全测试所涉及的工具和技术，将这些实践和技术一致和全面地应用到整个软件开发的过程中，会为所开发的软件带来更大程度的安全保证。</p>
          <br />
          <h1 style={{color: 'blue', textAlign: 'left' , marginLeft: '28%' }}>测试内容</h1>
          <p>孝道科技TCSEC团队通过多年来在应用安全领域的实践经验，创建了一整套安全开发流程，即信息技术安全开发生命周期流程。该流程包含有一系列的最佳实践和工具，已经被成功地应用在许多客户的开发项目中。</p>
          <img src="/static/services/sdl.png" />
          <img src="/static/services/sdl.jpg"  style={{ width:'1200px' }} />
        </div>
        
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />,
      </div>
    );
  }
}
