import React from 'react';
import PropTypes from 'prop-types';
import TweenOne, { TweenOneGroup } from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';

class Content extends React.Component {

  static propTypes = {
    id: PropTypes.string,
  };

  static defaultProps = {
    className: 'content5',
  };

  getChildrenToRender = (item, i) => {
    const id = `block${i}`;
    return (<li
      key={i}
      id={`${this.props.id}-${id}`}
    >
      <div className="content-wrapper">
        <span><img src={item.img1} height="100%" /></span>
        <a href={item.src1}><p>{item.content1}</p></a>
      </div>
      <div className="content-wrapper">
        <span><img src={item.img2} height="100%" /></span>
        <a href={item.src2}><p>{item.content2}</p></a>
      </div>
    </li>);
  }

  getEnterAnim = (e, isMode) => {
    const index = e.index;
    const delay = isMode ? index * 50 + 200 : index % 4 * 100 + Math.floor(index / 4) * 100 + 300;
    return { y: '+=30', opacity: 0, type: 'from', delay };
  };

  render() {
    const props = { ...this.props };
    const isMode = props.isMode;
    delete props.isMode;
    const dataArray = [
      { img1: '/static/cooperation/cooperation-1.png', content1: '中国信息安全测评中心', src1: 'http://www.itsec.gov.cn/', img2: '/static/cooperation/cooperation-2.png', content2: '国家信息安全漏洞库', src2: 'http://www.cnnvd.org.cn/' },
      { img1: '/static/cooperation/cooperation-3.png', content1: '阿里云', src1: 'http://www.aliyun.com/', img2: '/static/cooperation/cooperation-2.png', content2: '国家信息安全漏洞库', src2: 'http://www.cnnvd.org.cn/' },
      { img1: '/static/cooperation/cooperation-3.png', content1: '阿里云', src1: 'http://www.aliyun.com/', img2: '/static/cooperation/cooperation-2.png', content2: '国家信息安全漏洞库', src2: 'http://www.cnnvd.org.cn/' },
      { img1: '/static/cooperation/cooperation-3.png', content1: '阿里云', src1: 'http://www.aliyun.com/', img2: '/static/cooperation/cooperation-2.png', content2: '国家信息安全漏洞库', src2: 'http://www.cnnvd.org.cn/' },
      { img1: '/static/cooperation/cooperation-3.png', content1: '阿里云', src1: 'http://www.aliyun.com/', img2: '/static/cooperation/cooperation-2.png', content2: '国家信息安全漏洞库', src2: 'http://www.cnnvd.org.cn/' },
      { img1: '/static/cooperation/cooperation-3.png', content1: '阿里云', src1: 'http://www.aliyun.com/', img2: '/static/cooperation/cooperation-2.png', content2: '国家信息安全漏洞库', src2: 'http://www.cnnvd.org.cn/' },
      { img1: '/static/cooperation/cooperation-3.png', content1: '阿里云', src1: 'http://www.aliyun.com/', img2: '/static/cooperation/cooperation-2.png', content2: '国家信息安全漏洞库', src2: 'http://www.cnnvd.org.cn/' },
      { img1: '/static/cooperation/cooperation-3.png', content1: '阿里云', src1: 'http://www.aliyun.com/', img2: '/static/cooperation/cooperation-2.png', content2: '国家信息安全漏洞库', src2: 'http://www.cnnvd.org.cn/' },
      
      //{ img: '/static/cooperation/cnvd.jpg', content: '国家信息安全漏洞共享平台',src:'http://www.itsec.gov.cn/' },
      //{ img: '/static/cooperation/aliyun.jpg', content: '阿里云',src:'http://www.itsec.gov.cn/' },
    ];
    const childrenToRender = dataArray.map(this.getChildrenToRender);
    return (
      <div
        {...props}
        className="content-template-wrapper content5-wrapper"
      >
      
        <OverPack
          className={`content-template ${props.className}`}
        >
          <TweenOne
            animation={{ y: '+=30', opacity: 0, type: 'from', ease: 'easeOutQuad' }}
            //component="h1"
            key="h1"
            reverseDelay={300}
            id={`${props.id}-title`}
          >
            <h1>我们的合作生态</h1>
            <p>Our Cooperative Ecology</p>
          </TweenOne>
          {/* <TweenOne
            animation={{ y: '+=30', opacity: 0, type: 'from', delay: 200, ease: 'easeOutQuad' }}
            component="p"
            key="p"
            reverseDelay={200}
            id={`${props.id}-content`}
          >
            在这里用一段话介绍服务的案例情况
          </TweenOne> */}
          <div className="img-list">
          <TweenOneGroup
            className={`${props.className}-img-wrapper`}
            component="ul"
            key="ul"
            enter={(e) => {
              return this.getEnterAnim(e, isMode)
            }}
            leave={{ y: '+=30', opacity: 0, ease: 'easeOutQuad' }}
            id={`${props.id}-ul`}
          >
            {childrenToRender}
          </TweenOneGroup>
          </div>
        </OverPack>
      </div>
    );
  }
}


export default Content;
