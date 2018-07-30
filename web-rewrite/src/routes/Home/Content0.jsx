import React, { PropTypes } from 'react';
import { Button, Icon } from 'antd';
import QueueAnim from 'rc-queue-anim';
import TweenOne from 'rc-tween-one';
import OverPack from 'rc-scroll-anim/lib/ScrollOverPack';
import { Link } from 'react-router';

class Content extends React.Component {
  render() {
    const props = { ...this.props };
    delete props.isMode;
    return (
      <OverPack
        replay
        playScale={[0.3, 0.1]}
        {...props}
      >
        <QueueAnim
          type={['bottom', 'top']}
          delay={200}
          className={`${props.className}-wrapper`}
          key="text"
          id={`${props.id}-wrapper`}
        >
          <span
            className="title"
            key="title"
            id={`${props.id}-title`}
          ><h1>代码安全云</h1>
            {/* <img width="100%" src="https://zos.alipayobjects.com/rmsportal/HqnZZjBjWRbjyMr.png" /> */}
          
          </span>
          <p
            key="content"
            id={`${props.id}-content`}            
          >
            让代码安全更简单更普惠 
          </p>
          {/* <Link to="/about"><Button type="primary" key="button" id={`${props.id}-button`}>
            了解我们
          </Button></Link>
          <Link to="/anniversary"><Button type="primary">孝道两周年</Button></Link> */}
          <Button type="primary">进入我们 ></Button>
        </QueueAnim>
        <TweenOne
          animation={{ y: '-=20', yoyo: true, repeat: -1, duration: 1000 }}
          className={`${props.className}-icon`}
          key="icon"
        >
          <Icon type="down" />
        </TweenOne>
      </OverPack>
    );
  }
}

Content.propTypes = {
  className: PropTypes.string,
  id: PropTypes.string,
};

Content.defaultProps = {
  className: 'banner0',
};

export default Content;
