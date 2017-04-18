%%
%% This is file `softsys.cls',
%% 
\NeedsTeXFormat{LaTeX2e}[1999/12/01]
\ProvidesClass{softsys}[2016/04/07 v1.2 softsys class]
\RequirePackage{ifthen}
\newboolean{softsys@setautor}\setboolean{softsys@setautor}{false}%
\newboolean{softsys@setpruefnr}\setboolean{softsys@setpruefnr}{false}%
\newboolean{softsys@setort}\setboolean{softsys@setort}{false}%
\newboolean{softsys@setsprache}\setboolean{softsys@setsprache}{false}%
\newboolean{softsys@setcompiler}\setboolean{softsys@setcompiler}{false}%
\newboolean{softsys@setrechner}\setboolean{softsys@setrechner}{false}%
\newboolean{softsys@setos}\setboolean{softsys@setos}{false}%
\newboolean{softsys@setthema}\setboolean{softsys@setthema}{false}%
\newboolean{softsys@weiblich}\setboolean{softsys@weiblich}{false}%
\newboolean{softsys@settitle}\setboolean{softsys@settitle}{false}%
\newboolean{softsys@umlcd}\setboolean{softsys@umlcd}{false}%
\newboolean{softsys@umlsd}\setboolean{softsys@umlsd}{false}%
\newboolean{softsys@nassi}\setboolean{softsys@nassi}{false}%
\newboolean{softsys@tests}\setboolean{softsys@tests}{false}%
\newboolean{softsys@sourc}\setboolean{softsys@sourc}{false}%
\newcommand{\softsys@titel}{}
\newcommand{\softsys@pruefling}{}
\newcommand{\softsys@thema}{}
\newcommand{\softsys@autor}{}
\newcommand{\softsys@pruefnr}{}
\newcommand{\softsys@ort}{}
\newcommand{\softsys@jahr}{\the\year}
\newcommand{\softsys@sprache}{}
\newcommand{\softsys@compiler}{}
\newcommand{\softsys@rechner}{}
\newcommand{\softsys@os}{}
\newcommand{\Autor}[1]{%
  \renewcommand{\softsys@autor}{#1}%
  \setboolean{softsys@setautor}{true}}
\newcommand{\PruefungsNR}[1]{%
  \renewcommand{\softsys@pruefnr}{#1}%
  \setboolean{softsys@setpruefnr}{true}}
\newcommand{\Ausbildungsort}[1]{%
  \renewcommand{\softsys@ort}{#1}%
  \setboolean{softsys@setort}{true}}
\newcommand{\Programmiersprache}[1]{%
  \renewcommand{\softsys@sprache}{#1}%
  \setboolean{softsys@setsprache}{true}}
\newcommand{\Compiler}[1]{%
  \renewcommand{\softsys@compiler}{#1}%
  \setboolean{softsys@setcompiler}{true}}
\newcommand{\Rechner}[1]{%
  \renewcommand{\softsys@rechner}{#1}%
  \setboolean{softsys@setrechner}{true}}
\newcommand{\Betriebssystem}[1]{%
  \renewcommand{\softsys@os}{#1}%
  \setboolean{softsys@setos}{true}}
\newcommand{\Thema}[1]{%
  \renewcommand{\softsys@thema}{#1}%
  \setboolean{softsys@setthema}{true}}
\newcommand{\MW}[1]{%
  \ifthenelse{\equal{#1}{w}}
  {%then
    \setboolean{softsys@weiblich}{true}}%
  {%else
    \ifthenelse{\equal{#1}{m}}%
    {%then
      \setboolean{softsys@weiblich}{false}}%
    {%else
      \ClassError{softsys}{%
        Only 'w' or 'm' are allowed for MW\MessageBreak}}}}
\newcommand{\PruefungsJahr}[1]{\renewcommand{\softsys@jahr}{#1}}
\DeclareOption{10pt}{\PassOptionsToClass{10pt}{scrreprt}}
\DeclareOption{11pt}{\PassOptionsToClass{11pt}{scrreprt}}
\ProcessOptions
\LoadClass[12pt,twoside,BCOR=12mm]{scrreprt}
\RequirePackage{ifpdf}
\RequirePackage{ngerman}
\RequirePackage{fancyhdr}
\RequirePackage{tabularx}
\RequirePackage{alltt}
\RequirePackage{times}
\RequirePackage{mathptmx}
\ifpdf
\RequirePackage[pdftex]{graphicx,color}
  \RequirePackage[pdftex, pdfborder={0 0 0}]{hyperref}
\else
\RequirePackage{color}
\RequirePackage[dvips]{graphicx}
\RequirePackage[dvips, pdfborder={0 0 0}]{hyperref}
\fi
%
\DeclareOption{umlcd}{\setboolean{softsys@umlcd}{true}}%
\DeclareOption{umlsd}{\setboolean{softsys@umlsd}{true}}%
\DeclareOption{nassi}{\setboolean{softsys@nassi}{true}}%
\DeclareOption{tests}{\setboolean{softsys@tests}{true}}%
\DeclareOption{sourc}{\setboolean{softsys@sourc}{true}}%
\ProcessOptions%
%
\ifthenelse{\boolean{softsys@umlcd}}{
  \RequirePackage{tikz}
  \RequirePackage{pgf-umlcd}
}{}%
\ifthenelse{\boolean{softsys@umlsd}}{
  \RequirePackage{tikz}
  \RequirePackage{pgf-umlsd}
}{}%
\ifthenelse{\boolean{softsys@nassi}}{%
  \RequirePackage{struktex}%
}{}%
\ifthenelse{\boolean{softsys@tests}}{%
  \RequirePackage{tikz}%
  \RequirePackage{alltt}%
}{}%
\ifthenelse{\boolean{softsys@sourc}}{%
  \RequirePackage{listings}%
  \lstset{
    numbers=left,                                          % where to put the line-numbers; possible values are (none, left, right)
    numbersep=5pt,                                         % how far the line-numbers are from the code
    numberstyle=\footnotesize\color{black!20},             % the style that is used for the line-numbers
    keywordstyle=\bfseries,                                % the style that is used for keywords
    stringstyle=\itshape,                                  % the style that is used for strings
    commentstyle=\itshape\color{black!60},                 % the style that is used for comments
    morecomment=[l][\itshape\bfseries\color{black!60}]{\#} % the style that is used for preprocessor directives (e.g. #include ...)
  }
}{}%

%\setlength{\parindent}{0pt}
%\setlength{\parskip}{10pt}
%\setlength{\textwidth}{15cm}
%\setlength{\evensidemargin}{0.5cm}
%
\renewcommand{\headrulewidth}{0.4pt}
\renewcommand{\footrulewidth}{0.4pt}
\renewcommand{\chaptermark}[1]{\markboth{#1}{}}
%
\renewcommand\chapter{\if@openright\cleardoublepage\else\clearpage\fi
  \thispagestyle{empty}%
  \global\@topnum\z@
  \@afterindentfalse
  \secdef\@chapter\@schapter}
%
\newcommand\chapterS[1]{
  \addtocounter{chapter}{1}
  \phantomsection
  \makeatletter
  \addtocontents{toc}{\protect\contentsline{chapter}{%
      \protect\numberline{\thechapter}#1}{}{\@currentHref}}
  \makeatother
  \chapter*{Anhang \thechapter\\[1.2cm] #1}
}
% 
\newcommand\setpagestyle{%
  \pagestyle{fancy}
  \fancyhf{}
  \fancyhead[LO]{\bfseries \small{\leftmark}}
  \fancyhead[RE]{\bfseries \small{Dokumentation der Praktischen Arbeit \softsys@jahr}}
  \fancyhead[LE,RO]{\bfseries \small{\thepage}}
  \fancyfoot[RE]{\ifthenelse{\boolean{softsys@setthema}}{{\bfseries \small{\softsys@thema}}}{}}
  \ifthenelse{\boolean{softsys@setautor}}{
    \fancyfoot[LO]{\bfseries \small{\softsys@autor}}
  }{\ClassError{softsys}{Die Variablen AUTOR wurde nicht gesetzt !!!\MessageBreak}{}}
}
%
\AtBeginDocument{\setpagestyle}
%
\newcommand\setgeschlecht{%
  \ifthenelse{\boolean{softsys@settitle}}{}{%
    \setboolean{softsys@settitle}{true}%
    \ifthenelse{\boolean{softsys@weiblich}}%
    {%then
      \renewcommand{\softsys@titel}{
        Dokumentation der Praktischen Arbeit\\%
        zur Pr"ufung zur\\%
        Mathematisch-technischen Softwareentwicklerin}%
      \renewcommand{\softsys@pruefling}{der Pr"ufungsteilnehmerin}}%
    {%else
      \renewcommand{\softsys@titel}{
        Dokumentation der Praktischen Arbeit\\%
        zur Pr"ufung zum\\%
        Mathematisch-technischen Softwareentwickler}%
      \renewcommand{\softsys@pruefling}{des Pr"ufungsteilnehmers}}}}
%
\renewcommand\maketitle{%
  \pagestyle{empty}
  \@maketitle\cleardoublepage
  \setpagestyle
}
%
\def\@maketitle{%
  \setgeschlecht
  \newpage
  \vskip 2em%
  \begin{center}%
    \rule{0.95\textwidth}{1pt}%
    \vskip .3cm%
    \begin{minipage}{0.9\textwidth}
      \renewcommand{\baselinestretch}{1.3}
      \begin{center}
        \Large \textbf{\softsys@titel}
      \end{center}
    \end{minipage}
    \vskip .3cm%
    \rule{0.95\textwidth}{1pt}\vfill\vfill
    \ifthenelse{\boolean{softsys@setthema}}{{\Large%
      \textbf{Thema:\begin{tabular}[t]{l}\softsys@thema\end{tabular}}}}{}
    \vskip .5cm
    \today
    \ifthenelse{\boolean{softsys@setautor}}{
      \vfill\vfill\vfill{\large \textbf{\softsys@autor}}
    }{\ClassError{softsys}{Die Variablen AUTOR wurde nicht gesetzt !!!\MessageBreak}{}}
    \vfill\vfill\vfill
    \begin{tabular}{p{6cm}p{6cm}}
      \ifthenelse{\boolean{softsys@setpruefnr}}{
        \hfill Pr"ufungs-Nummer: & \softsys@pruefnr\\[.3cm]
      }{\ClassError{softsys}{Die Variablen PRUEFUNGSNR wurde nicht gesetzt !!!\MessageBreak}{}}
      \ifthenelse{\boolean{softsys@setsprache}}{
        \hfill Programmiersprache: & \softsys@sprache\\[.3cm]
      }{\ClassError{softsys}{Die Variablen PROGRAMMIERSPRACHE wurde nicht gesetzt !!!\MessageBreak}{}}
      \ifthenelse{\boolean{softsys@setort}}{
        \hfill Ausbildungsort: & \hskip -5pt \begin{tabular}[t]{l}\softsys@ort\end{tabular}
      }{\ClassError{softsys}{Die Variablen AUSBILDUNGSORT wurde nicht gesetzt !!!\MessageBreak}{}}
    \end{tabular}
  \end{center}}

\newcommand{\Ginclude}[1]{\include{#1}\cleardoublepage}
%
\newcommand\makeenvironment{%
  \begin{center}%
    \begin{tabular}{p{3.5cm}cp{9cm}}%
      \ifthenelse{\boolean{softsys@setsprache}}{
        Programmiersprache & : & \softsys@sprache\\%
      }{\ClassError{softsys}{Die Variablen PROGRAMMIERSPRACHE wurde nicht gesetzt !!!\MessageBreak}{}}
      \ifthenelse{\boolean{softsys@setcompiler}}{
        Compiler & : & \softsys@compiler\\%
      }{\ClassError{softsys}{Die Variablen COMPILER wurde nicht gesetzt !!!\MessageBreak}{}}
      \ifthenelse{\boolean{softsys@setrechner}}{
        Rechner & : & \softsys@rechner\\%
      }{\ClassError{softsys}{Die Variablen RECHNER wurde nicht gesetzt !!!\MessageBreak}{}}
      \ifthenelse{\boolean{softsys@setos}}{
        Betriebssystem & : & \softsys@os\\%
      }{\ClassError{softsys}{Die Variablen BETRIEBSSYSTEM wurde nicht gesetzt !!!\MessageBreak}{}}
    \end{tabular}%
  \end{center}}
%
\newsavebox{\mountbox}
\newenvironment{testbox}[1][]{\def\testboxPara{#1}%
  \begin{lrbox}{\mountbox}\begin{minipage}{0.9\textwidth}\begin{alltt}}{\end{alltt}\end{minipage}\end{lrbox}%
  \begin{tikzpicture}%
    \node[draw=black!30, fill=black!5, very thin, rounded corners, inner sep=10pt, inner ysep=20pt](box){\usebox{\mountbox}};%
    \ifx\fooNoI\empty\else\node[fill=black!30, text=black,rounded corners, right=10pt] at (box.north west) {\testboxPara};\fi%
  \end{tikzpicture}%
}%
%
\newread\file%
\newcount\lineNRtestbox%
\newcommand{\testboxinc}[3][999]{%
  \begin{testbox}[#2]#3%
    \lineNRtestbox=1%
    \openin\file=#2%
      \read\file to\fileline % Reads a line of the file into \fileline
      \loop\unless\ifeof\file%
        \ifnum\lineNRtestbox>#1%
          [...]%
          \let\iterate\relax%
        \else%
          %\textit{\the\lineNRtestbox~} % Zeilennummer vorne
          \fileline%
        \fi%
        \advance\lineNRtestbox by 1\relax%
        \read\file to\fileline % Reads a line of the file into \fileline%
      \repeat%
    \closein\file%
  \end{testbox}}%
%
\makeatletter%
%
\newcounter{sourcecode}%
\newcommand{\sourcecode}[1]{%
  \refstepcounter{sourcecode}%
  \bigskip{\size@section\sectfont C\thesourcecode:~#1}%
  \addcontentsline{sou}{section}{\protect\numberline{C\thesourcecode:}#1}\par}%
\newcounter{test}%
\newcommand{\test}[1]{%
  \refstepcounter{test}%
  \bigskip\noindent{\size@subsubsection\sectfont T\thetest:~#1}%
  \addcontentsline{tes}{section}{\protect\numberline{T\thetest:}#1}\par}%
%
\newcommand{\listofSources}{\par\begingroup\@starttoc{sou}\endgroup\bigskip}%
\newcommand{\listofTests}{\par\begingroup\@starttoc{tes}\endgroup\bigskip}%
%
\makeatother%
%
