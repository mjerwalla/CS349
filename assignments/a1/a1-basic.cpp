/*See associated makefile for compiling instructions
*/
#include <iostream>
#include <list>
#include <cstdlib>
#include <unistd.h>
#include <vector>
#include <sys/time.h>
#include <sstream>
#include <X11/Xlib.h>
#include <X11/Xutil.h>

using namespace std;



unsigned long now() {
    timeval tv;
    gettimeofday(&tv, NULL);
    return tv.tv_sec * 1000000 + tv.tv_usec;
}


const int Border = 5;
const int BufferSize = 10;
// interval1 refers to the interval between blocks of width 50px
const int interval1 = 850/3;
// interval2 refers to the interval between blocks of width 20px
const int interval2 = 850/4;
// interval3 refers to the interval between blocks of with 100px
const int interval3 = 850/2; 
// levelNum stores the current level of the player
int levelNum = 1;
int FPS = 30;
struct XInfo {
    Display*  display;
    Window   window;
    GC       gc;
};


/* ============ Class definitions begin============= */
// An abstract class representing displayable things.
class Displayable {
public:
    virtual void paint(XInfo& xinfo) = 0;

};

// A text displayable
class Text : public Displayable {
public:
    virtual void paint(XInfo& xinfo) {
        GC gc = XCreateGC(xinfo.display, xinfo.window, 0,0);
        XFontStruct * font = font = XLoadQueryFont (xinfo.display, "12x24");
        XSetFont (xinfo.display, gc, font->fid);
        XDrawString( xinfo.display, xinfo.window, gc,
                          this->x, this->y, this->s.c_str(), this->s.length() );
    }
    Text(string s, int x=675, int y=20): s(s), x(x), y(y)  {}

    void updateText() { 
        stringstream ss; 
        ss << levelNum ; 
        this->s = "Level: "+ss.str();
    }

private:
    int x;
    int y;
    string s; // string to show
};


// A box displayable 
class Box : public Displayable {
public:
    virtual void paint(XInfo& xinfo) {
        GC gc = XCreateGC(xinfo.display, xinfo.window, 0,0);
        // note the trick to pass a stl vector as an C array
        XFillRectangle(xinfo.display, xinfo.window, gc,
                   this->x, this->y, this->w,  // vector of points
                   this->h); // use absolute coordinates
    }

    // get and set functions to retrieve and mutate coordinates & width of box
    int getX(){ 
        return this->x;
    }

    int getY(){
        return this->y;
    }

    int getW(){ 
        return this->w;
    }

    // int getH(){
    //     return this->h;
    // }

    void setX(int x) {
        this->x=x;
        // cout << x << endl; 
    }

    void setY(int y) {
        this->y=y;
    }

    Box(int x , int y, int w, int h): x(x), y(y), w(w), h(h)  {}

    ~Box(){}

    private: 
        int x;
        int y; 
        int w; 
        int h; 
};

// A displayable frog
class Frog : public Displayable {
public:
    virtual void paint(XInfo& xinfo) {
        GC gc = XCreateGC(xinfo.display, xinfo.window, 0,0);
        XFillRectangle(xinfo.display, xinfo.window, gc,
                   this->x, this->y, 50,  // vector of points
                   50); // use absolute coordinates
    }
    // get and set functions to retireve and mutate coordinates
    int getX(){ 
        return this->x;
    }

    int getY(){
        return this->y;
    }

    void setX(int x) {
        this->x=x;
    }

    void setY(int y) {
        this->y=y;
    }

    Frog(int x= 425, int y=200): x(x), y(y) {}

    ~Frog(){}

    private: 
        int x;
        int y; 
};


//================Function definitions begin===========================


//collisions(list<Box*>, Frog*) accepts an array of box pointers and a frog 
// and checks if the frog collides with any of the blocks
bool collision(list<Box*> collisionList, Frog *frog) {
    list<Box*>::const_iterator begin = collisionList.begin();
    list<Box*>::const_iterator end = collisionList.end();
    while (begin != end) {
        Box* block = *begin;
        // extracting coordinates of block and frog
        int frogX = frog->getX();
        int frogY = frog->getY();
        int x = block->getX(); 
        int y = block->getY();
        int w = block->getW();
        int difference;
        // When width of block == 50
        if (w == 50) {
            if (x - frogX <= 50 && x - frogX >= -50 && y == frogY){
                return true; 
            }
        } // When width of block == 20 
        else if (w == 20) {
            if (x - frogX <= 50  && x - frogX >= -20 && y == frogY){
            return true; 
            }
        } // When widt of block == 100
        else {
            if (x - frogX <= 50 && x - frogX >= -100 && y == frogY){
                return true; 
            }
        }
        begin++;
    }
    return false; 
}

// boxMove(Box, string, int) accetps a Box, a string which denotes direct,
// and layer which enables blocks to move either left of right depending on the
//  layer 
void boxMove(Box &box, string direction, int layer) {
    int x = box.getX(); 
    // To move block right 
    if (direction == "right"){
        if (x >= 850) {
            // Top-most layer of blocks
            if (layer == 1){
                x = 0 - interval1;
            } // Lowest layer of blocks 
            else {
                x = 0 - interval3;
            }
        }
        box.setX(x+levelNum);
    } // To move block left 
    else if (direction == "left"){
        if (x <= -20) {
            if (layer == 2){
                x = 5*interval2-20;
                // box.setX(x);
            }
        }
        box.setX(x-levelNum);
    }
}

// frogMove(Frog, string) moves the frog in either one of 4 directions
void frogMove(Frog &frog, string direction) {
    int x = frog.getX();
    int y = frog.getY();
    // Move frog up
    if (direction == "up") {
        // Check if frog is not on top of screen 
        if (y > 0) {
            frog.setY(y-50);
        }
    } // Move frog down
    else if (direction == "down") {
        // Check if frog is not in goal area
        if (y >= 50) {
            // Check if frog is not at the bottom of the window
            if (y < 200) {
                frog.setY(y+50);
            }
        }
    } // Move frog left 
    else if (direction == "left") {
        // Check if frog has room to move left
        if (x > 25) {
            frog.setX(x-50); 
        } else if (x == 25) {
            frog.setX(x-25);
        }
    } // Move frog right 
    else if (direction == "right") {
        // Check if frog has room to move right
        if (x < 775){
            frog.setX(x+50); 
        } else if (x == 775) {
            frog.setX(x+25);
        }
    }
}

// Function to put out a message on error exits.
void error( string str ) {
    cerr << str << endl;
    exit(0);
}

// Function to repaint a display list
void repaint( list<Displayable*> dList, XInfo& xinfo) {
    list<Displayable*>::const_iterator begin = dList.begin();
    list<Displayable*>::const_iterator end = dList.end();
    XClearWindow( xinfo.display, xinfo.window );
    while ( begin != end ) {
        Displayable* d = *begin;
        d->paint(xinfo);
        begin++;
    }
    XFlush( xinfo.display );
}

// The loop responding to events from the user.
void eventloop(XInfo& xinfo) {
    XEvent event;
    KeySym key;
    char text[BufferSize];
    list<Displayable*> dList; 
    // List to store all blocks 
    list<Box*> collisionList;  
    // Initializing frog     
    Frog* frog = new Frog();
    dList.push_back(frog);
    // Creating boxes
    Box* ubox1 = new Box(0, 50, 50, 50);
    Box* ubox2 = new Box(ubox1->getX() + interval1, 50, 50, 50);
    Box* ubox3 = new Box(ubox2->getX() + interval1, 50, 50, 50);
    Box* ubox4 = new Box(ubox1->getX() - interval1, 50, 50, 50);
    Box* mbox1 = new Box(0, 100, 20, 50);
    Box* mbox2 = new Box(mbox1->getX() + interval2, 100, 20, 50);
    Box* mbox3 = new Box(mbox2->getX() + interval2, 100, 20, 50);
    Box* mbox4 = new Box(mbox3->getX() + interval2, 100, 20, 50);
    Box* mbox5 = new Box(mbox4->getX() + interval2, 100, 20, 50);
    Box* lbox1 = new Box(0, 150, 100, 50);
    Box* lbox2 = new Box(mbox1->getX() + interval3, 150, 100, 50);
    Box* lbox3 = new Box(mbox1->getX() - interval3, 150, 100, 50);
    // Appending boxes to dList
    dList.push_back(ubox1);
    dList.push_back(ubox2);
    dList.push_back(ubox3);
    dList.push_back(ubox4);
    dList.push_back(mbox1);
    dList.push_back(mbox2);
    dList.push_back(mbox3);
    dList.push_back(mbox4);
    dList.push_back(mbox5);
    dList.push_back(lbox1);
    dList.push_back(lbox2);
    dList.push_back(lbox3);
    // Appending boxes to collisionList
    collisionList.push_back(ubox1);
    collisionList.push_back(ubox2);
    collisionList.push_back(ubox3);
    collisionList.push_back(ubox4);
    collisionList.push_back(mbox1);
    collisionList.push_back(mbox2);
    collisionList.push_back(mbox3);
    collisionList.push_back(mbox4);
    collisionList.push_back(mbox5);
    collisionList.push_back(lbox1);
    collisionList.push_back(lbox2);
    collisionList.push_back(lbox3);
    stringstream ss;
    ss << levelNum; 
    string level = "Level: " + ss.str();
    // Initialize Level string
    Text *currLevel = new Text(level);
    dList.push_back(currLevel);
    unsigned long lastRepaint = 0; 
    
    while ( true ) {

        if (XPending(xinfo.display) > 0) {
            //event / keypress will be handled here.
            XNextEvent( xinfo.display, &event );
            switch ( event.type ) {
            case KeyPress: // any keypress
                int i = XLookupString( 
                (XKeyEvent*)&event, text, BufferSize, &key, 0 );
                cout << "KeySym " << key
                     << " text='" << text << "'"
                     << " at " << event.xkey.time
                     << endl;
                // Checking if q was pressed
                if ( i == 1 && text[0] == 'q' ) {
                    cout << "Terminated normally." << endl;
                    XCloseDisplay(xinfo.display);
                    return;
                }
                // Checking if n is pressed
                if ( i == 1 && text[0] == 'n' ) {
                    // If n is pressed, checking if in goal area to increment level
                    if (frog->getY() < 50){
                        ++levelNum; 
                        cout << "Level: " << levelNum << endl;
                        frog->setX(425);
                        frog->setY(200); 
                        currLevel->updateText();
                        // XCloseDisplay(xinfo.display);
                        // return;
                    }
                }
                switch(key){
                    case XK_Up:
                        cout << "Up" << endl;
                        frogMove(*frog, "up"); 
                        break;
                    case XK_Down:
                        cout << "Down" << endl;
                        frogMove(*frog, "down"); 
                        break;
                    case XK_Left:
                        cout << "Left" << endl;
                        frogMove(*frog, "left");
                        break;
                    case XK_Right:
                        cout << "Right" << endl;
                        frogMove(*frog, "right");
                        break;
                }
                break;
            }
        } 
        // Checking for collision 
        if (collision(collisionList, frog)) {
            levelNum = 1; 
            frog->setX(425);
            frog->setY(200); 
            currLevel->updateText();
        }
        // Moving blocks left/right
        boxMove(*ubox1, "right", 1);
        boxMove(*ubox2, "right", 1);
        boxMove(*ubox3, "right", 1);
        boxMove(*ubox4, "right", 1);
        boxMove(*mbox1, "left", 2);
        boxMove(*mbox2, "left", 2);
        boxMove(*mbox3, "left", 2);
        boxMove(*mbox4, "left", 2);
        boxMove(*mbox5, "left", 2); 
        boxMove(*lbox1, "right", 3);
        boxMove(*lbox2, "right", 3);
        boxMove(*lbox3, "right", 3);
        unsigned long end = now();
        if (end - lastRepaint > 1000000 / FPS) {
            //making/drawing everything
            Pixmap pixmap;
            pixmap = xinfo.window;
            // clear background
            XClearWindow(xinfo.display, pixmap);
            repaint(dList, xinfo);
            XFlush( xinfo.display );
            lastRepaint = now(); // remember when the paint happened
        }
        if (XPending(xinfo.display) == 0) {
            usleep(1000000 / FPS - (end - lastRepaint));
        }
    }
    XCloseDisplay(xinfo.display);
}



//  Create the window;  initialize X.
void initX(int argc, char* argv[], XInfo& xinfo) {
    xinfo.display = XOpenDisplay( "" );
    if ( !xinfo.display ) {
        error( "Can't open display." );
    }
    int screen = DefaultScreen( xinfo.display );
    unsigned long background = WhitePixel( xinfo.display, screen );
    unsigned long foreground = BlackPixel( xinfo.display, screen );
 
    XSizeHints hints;
    hints.x = 100;
    hints.y = 100;
    hints.width = 850;
    hints.height = 250;
    hints.flags = PPosition | PSize;
    xinfo.window = XCreateSimpleWindow( xinfo.display,
                                        DefaultRootWindow( xinfo.display ),
                                        hints.x, 
                                        hints.y, 
                                        hints.width, 
                                        hints.height,
                                        Border, 
                                        foreground, 
                                        background );
    
    XSetStandardProperties( xinfo.display, 
                            xinfo.window,
                            "Frog",
                            "Frog",
                            None,
                            argv,
                            argc,
                            &hints );

    xinfo.gc = XCreateGC (xinfo.display, xinfo.window, 0, 0 );
    XSetBackground( xinfo.display, xinfo.gc, background );
    XSetForeground( xinfo.display, xinfo.gc, foreground );
    // Tell the window manager what input events you want.
    XSelectInput( xinfo.display, xinfo.window,
                  KeyPressMask );
    XMapRaised( xinfo.display, xinfo.window );
}


int main ( int argc, char* argv[] ) {
    stringstream ss; 
    // Checking if FPS is provided as input
    if (argc > 1) {
        ss.str(argv[1]);
        ss >> FPS; 
    }
    XInfo xinfo;
    initX(argc, argv, xinfo);
    eventloop(xinfo);
}