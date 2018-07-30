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
        <div className="web">
        <div className="banner"><h1>安全风险评估</h1></div>
        <div className="Myservice">
          <div className="Myservice_All">
            <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
            <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>孝道科技TCSEC团队基于国际信息安全体系进行信息安全风险评估服务，帮助企业识别信息资产及业务流程的信息安全弱点，认识现状与信息安全标准及规范要求的差距，并帮助用户制订改进规划方案，同时可提供信息安全保障体系或管理体系的咨询服务。</p>
            </div>
          </div>
        </div>
        <div id="safetyAssessment"><img src="/static/services/safetyAssessment.jpg" alt="风险评估"/></div>
        <div className="mainContent">
        <div className="safety_assessment">
          <h1>评估内容</h1>
          <h2>—————</h2>
          <div className="safety_text">
            <div className="safety_text1">
              <h3>基础架构安全评估</h3>
              <br />
              <p>信息资产（基础架构）风险评估，主要评估基础IT系统的安全性，包括物理安全、主机操作系统安全、网络架构安全、数据通讯安全、密钥管理、信息认证与保密、入侵检测等内容，识别、发现及分析安全风险，并提供加固建议报告，保证信息资产（基础架构）的安全。</p>
            </div>
            <div className="safety_img1"><img src="/static/services/web-1.png" /></div>
            <div className="safety_img2"><img src="/static/services/web-3.png" /></div>
            <div className="safety_text2">
              <h3>应用系统安全评估</h3>
              <br />
              <p>从系统研发、身份鉴别、访问控制、流程安全、异常处理、备份与故障恢复、密码安全、输入输出合法性、安全审计、数据安全性十个方面评价应用系统的整体安全，发现应用程序在设计、运营和管理方面存在的安全风险，并提供修改建议，确保应用系统自身的安全。</p>
            </div>
            <div className="safety_text3">
              <h3>业务流程安全评估</h3>
              <br />
              <p>从业务层面，信息安全应当还应关注IT支撑的业务流程（如：跨部门业务处理流程），支撑业务过程的IT流程（即纵向IT过程，如：软件开发、测试和上线流程），也有支撑IT本身的绩效及安全的IT流程（即横向IT过程，如变更管理过程）。通过风险评估，分析其存在的风险和对业务影响，提出了风险处置方案，建立适用的IT流程控制目标和控制措施。</p>      
            </div>
            <div className="safety_img1"><img src="/static/services/web-4.png" /></div>
          </div>
          
        </div>
        </div>

        <div className="foot">
           <div className="foot_All">
              <div className="foot_one"><h1>服务经验</h1><h1>团队成员</h1><h1>成功案例</h1><h1>客户好评</h1></div>
              <div className="foot_two"><h1>10年</h1><h1>35</h1><h1>3023</h1><h1>5230</h1></div>
            </div>
        </div>  
        
        
        </div>  
        
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />,
      </div>
    );
  }
}
