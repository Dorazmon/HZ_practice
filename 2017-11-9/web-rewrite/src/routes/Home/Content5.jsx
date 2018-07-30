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
        <a href={item.src1} target="_blank" rel="noopener noreferrer"><p>{item.content1}</p></a>
      </div>
      <div className="content-wrapper">
        <span><img src={item.img2} height="100%" /></span>
        <a href={item.src2} target="_blank" rel="noopener noreferrer"><p>{item.content2}</p></a>
      </div>
    </li>);
  }

  getEnterAnim = (e, isMode) => {
    const index = e.index;
    const delay = isMode ? index * 50 + 200 : index % 4 * 100 + Math.floor(index / 4) * 100 + 300;
    return { y: '+=30', opacity: 0, type: 'from', delay };
  };

  scroll=(e) => {
    //console.log(e);
    if (e.key === "3" && e.type === "appear"){
      $(function(){
        $("#scrolllist2").scrollForever();
      });
    }

  }

  render() {
    const props = { ...this.props };
    const isMode = props.isMode;
    delete props.isMode;
    const dataArray = [
      { img1: '/static/cooperation/cooperation-1.png', content1: '中国信息安全测评中心', src1: 'http://www.itsec.gov.cn/', img2: '/static/cooperation/cooperation-5.png', content2: '安全脉搏', src2: 'https://www.secpulse.com/'},
      { img1: '/static/cooperation/cooperation-7.png', content1: '国家信息安全漏洞共享平台', src1: 'http://www.cnvd.org.cn/', img2: '/static/cooperation/cooperation-9.png', content2: 'Freebuf', src2: 'http://www.freebuf.com/' },
      { img1: '/static/cooperation/cooperation-2.png', content1: '国家信息安全漏洞库', src1: 'http://www.cnnvd.org.cn/', img2: '/static/cooperation/cooperation-6.png', content2: '看雪论坛', src2: 'https://www.pediy.com/' },
      { img1: '/static/cooperation/cooperation-8.png', content1: '国家互联网应急中心', src1: 'http://www.cert.org.cn/', img2: '/static/cooperation/cooperation-11.png', content2: '爱春秋', src2: 'http://www.ichunqiu.com/' },
      { img1: '/static/cooperation/cooperation-4.png', content1: '阿里云', src1: 'http://www.aliyun.com/', img2: '/static/cooperation/cooperation-3.png', content2: '腾讯云', src2: 'https://cloud.tencent.com/' },
      //{ img1: '', content1: '腾讯云', src1: 'https://cloud.tencent.com/', img2: '/static/cooperation/cooperation-10.png', content2: '安全牛', src2: 'http://www.aqniu.com/' },
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
          <div className="img-list" id="scrolllist2">
          <TweenOneGroup
            className={`${props.className}-img-wrapper`}
            component="ul"
            key="ul"
            enter={(e) => {
              return this.getEnterAnim(e, isMode)
            }}
            leave={{ y: '+=30', opacity: 0, ease: 'easeOutQuad' }}
            id={`${props.id}-ul`}
            onEnd={this.scroll.bind(this)}
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
